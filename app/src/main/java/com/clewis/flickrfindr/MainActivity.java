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
import android.view.MenuItem;
import android.widget.ImageView;

import com.clewis.flickrfindr.datamodel.Photo;
import com.clewis.flickrfindr.fullscreen.ImageViewerFragment;
import com.clewis.flickrfindr.search.SearchCallback;
import com.clewis.flickrfindr.search.SearchFragment;

public class MainActivity extends AppCompatActivity implements SearchCallback {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, SearchFragment.Companion.newInstance(), SearchFragment.NAME)
                .commit();


//        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_view);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onImageClicked(@NonNull Photo photo, @NonNull ImageView imageView) {
        //image has not yet loaded, do not handle image clicks
        if (imageView.getDrawable() == null) {
            return;
        }
        float aspectRatio = (float) imageView.getDrawable().getIntrinsicWidth() / imageView.getDrawable().getIntrinsicHeight();

        Fragment nextFragment = ImageViewerFragment.Companion.newInstance(photo, aspectRatio);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragmentTransaction.addSharedElement(imageView, imageView.getTransitionName());

            Fragment previousFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (previousFragment != null && previousFragment.isAdded()) {
                Transition exitFade = new Fade();
                previousFragment.setExitTransition(exitFade);
            }
        }

        fragmentTransaction.replace(R.id.fragment_container, nextFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onImageSaved(@NonNull Photo photo) {

    }
}
