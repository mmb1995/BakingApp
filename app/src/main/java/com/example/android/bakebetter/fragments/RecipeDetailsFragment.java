package com.example.android.bakebetter.fragments;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.model.Step;
import com.example.android.bakebetter.utils.ConnectivityHelper;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailsFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "RecipeDetailsFragment";

    private static final String ARG_RECIPE_STEP = "step";
    private static final String BUNDLE_VIDEO_POSITION = "videoPosition";
    private static final String BUNDLE_SET_PLAY_WHEN_READY = "set_play_when_ready";

    @BindView(R.id.recipeStepTitle)
    TextView mTitleTextView;

    @BindView(R.id.recipeStepContent)
    TextView mContentTextView;

    @BindView(R.id.RecipeVideoPlayer)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.cardView)
    CardView mDescriptionCardView;

    @BindView(R.id.details_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private SimpleExoPlayer mExoPlayer;
    private Step mStep;
    private long mVideoPosition = -1;
    private boolean mSetPlayWhenReady;


    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeDetailsFragment.
     */
    public static RecipeDetailsFragment newInstance(Step step) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE_STEP, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStep = getArguments().getParcelable(ARG_RECIPE_STEP);
        }

        if (savedInstanceState != null) {
            mVideoPosition = savedInstanceState.getLong(BUNDLE_VIDEO_POSITION);
            mSetPlayWhenReady = savedInstanceState.getBoolean(BUNDLE_SET_PLAY_WHEN_READY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, rootView);
        mSwipeRefresh.setOnRefreshListener(this);

        // Check if there is a video to play
        if (mStep.getVideoURL() != null && !mStep.getVideoURL().equals("")) {
            // There is a video
            setUpVideoPlayer();
        } else {
            // There is no video url provides so remove player view
            mPlayerView.setVisibility(View.GONE);
        }

        setUpUi();
        return rootView;
    }

    /**
     * Display details about the current step
     */
    private void setUpUi() {
        if (!isFullScreen() && mStep != null) {
            mTitleTextView.setText(mStep.getShortDescription());
            mContentTextView.setText(mStep.getDescription());
        } else {
            mDescriptionCardView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mExoPlayer != null) {
            // Resume the video where the user left off
            mExoPlayer.setPlayWhenReady(mSetPlayWhenReady);
            mExoPlayer.seekTo(mVideoPosition);
        } else {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(BUNDLE_VIDEO_POSITION, mVideoPosition);
        outState.putBoolean(BUNDLE_SET_PLAY_WHEN_READY, mSetPlayWhenReady);
    }

    /**
     * Displays the video associated with the recipe if the phone is in landscape orientation
     */
    private void setUpVideoPlayer() {
        if (isFullScreen()) {
            // This means the phone is in landscape orientation and should display the video in fullscreen
            hideSystemUI();
            showVideoInFullScreen();
        }
        initializePlayer();
    }

    private boolean isFullScreen() {
        return Objects.requireNonNull(getContext()).getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE
                && !getContext().getResources().getBoolean(R.bool.isTablet);
    }

    private void initializePlayer() {
        if (mExoPlayer == null && mPlayerView.getVisibility() != View.GONE) {
            // Check for Internet connection
            if (!ConnectivityHelper.isConnected(Objects.requireNonNull(getContext()))) {
                displayErrorMessage();
            } else {
                // Set up Exoplayer
                Uri mediaUri = Uri.parse(mStep.getVideoURL());

                // Creates an instance of the ExoPlayer
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
                mPlayerView.setPlayer(mExoPlayer);
                Log.i(TAG, "autoplay: " + mSetPlayWhenReady);
                mExoPlayer.setPlayWhenReady(mSetPlayWhenReady);

                // Prepare the MediaSource
                String userAgent = Util.getUserAgent(getActivity(), "BakeBetter");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        Objects.requireNonNull(getActivity()), userAgent), new DefaultExtractorsFactory(), null, null);

                // Prepare the Exoplayer
                mExoPlayer.prepare(mediaSource);
                if (mVideoPosition != -1) {
                    // Resumes video play if applicable
                    mExoPlayer.seekTo(mVideoPosition);
                }
            }
        }
    }

    /**
     * Releases ExoPlayer instance
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            savePlayerState();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    /**
     * Used by the Viewpager to ensure assets are released when the user swipes
     */
    public void loseFocus() {
        releasePlayer();
    }

    /**
     * Called by the ViewPager to initialize assets for incoming fragment
     */
    public void gainFocus() {
        initializePlayer();
    }

    private void savePlayerState() {
        if (mExoPlayer != null) {
            mVideoPosition = mExoPlayer.getCurrentPosition();
            mSetPlayWhenReady = mExoPlayer.getPlayWhenReady();
        }
    }

    /**
     * Hides the system ui to allow fullscreen video
     */
    private void hideSystemUI() {
        // hide the action bar
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }

    /**
     * Displays the video in fullscreen when phone is in landscape orientation
     */
    private void showVideoInFullScreen() {
        // hide the description cardview
        mDescriptionCardView.setVisibility(View.GONE);

        // Enable fullscreen
        ViewGroup.LayoutParams params = mPlayerView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        mPlayerView.setLayoutParams(params);
    }

    /**
     * Checks for an internet connection and tries to reload data if possible
     */
    @Override
    public void onRefresh() {
        if (ConnectivityHelper.isConnected(Objects.requireNonNull(getContext()))) {
            setUpVideoPlayer();
            setUpUi();
            mSwipeRefresh.setRefreshing(false);
        } else {
            mSwipeRefresh.setRefreshing(false);
            displayErrorMessage();
        }
    }

    /**
     * Inform user that their is no Internet connection available
     */
    private void displayErrorMessage() {
        Toast.makeText(getContext(), R.string.toast_error_no_internet,
                Toast.LENGTH_LONG).show();
    }
}
