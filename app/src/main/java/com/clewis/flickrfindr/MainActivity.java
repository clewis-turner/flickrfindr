package com.clewis.flickrfindr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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
                .replace(R.id.fragment_container, SearchFragment.Companion.newInstance())
                .commit();


//        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_view);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onImageClicked(@NonNull Photo photo) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ImageViewerFragment.Companion.newInstance(photo))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onImageSaved(@NonNull Photo photo) {

    }
}
