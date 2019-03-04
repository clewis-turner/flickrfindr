package com.clewis.flickrfindr;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.clewis.flickrfindr.datamodel.Photo;
import com.clewis.flickrfindr.feature.fullscreen.SingleImageViewerFragment;
import com.clewis.flickrfindr.feature.saved.SavedImagesFragment;
import com.clewis.flickrfindr.feature.base.ImageCallback;
import com.clewis.flickrfindr.feature.search.SearchFragment;

public class MainActivity extends AppCompatActivity implements ImageCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showHome();

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onImageClicked(@NonNull Photo photo, @NonNull ImageView imageView) {
        //image has not yet loaded, do not handle image clicks
        if (imageView.getDrawable() == null) {
            return;
        }
        float aspectRatio = (float) imageView.getDrawable().getIntrinsicWidth() / imageView.getDrawable().getIntrinsicHeight();

        Fragment nextFragment = SingleImageViewerFragment.Companion.newInstance(photo, aspectRatio);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragmentTransaction.addSharedElement(imageView, imageView.getTransitionName());

            Fragment previousFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (previousFragment != null && previousFragment.isAdded()) {
                Transition exitFade = new Fade();
                exitFade.setStartDelay(150);
                previousFragment.setExitTransition(exitFade);
            }
        }

        fragmentTransaction.replace(R.id.fragment_container, nextFragment, SingleImageViewerFragment.NAME)
                .addToBackStack(null)
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        showHome();
                        return true;
                    case R.id.navigation_dashboard:
                        showSavedImages();
                        return true;
                }
                return false;
            };

    private void showHome() {
        //leave full screen fragment if applicable
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SearchFragment.NAME);
        if (fragment == null) {
            fragment = SearchFragment.Companion.newInstance();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .show(fragment)
                .replace(R.id.fragment_container, fragment, SearchFragment.NAME)
                .commit();

    }

    private void showSavedImages() {
        //leave full screen fragment if applicable
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }

        Fragment searchFragment = getSupportFragmentManager().findFragmentByTag(SearchFragment.NAME);
        if (searchFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, SavedImagesFragment.Companion.newInstance(), SavedImagesFragment.NAME)
                .commit();
    }
}
