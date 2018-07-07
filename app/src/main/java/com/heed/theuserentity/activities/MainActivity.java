package com.heed.theuserentity.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.heed.theuserentity.R;
import com.heed.theuserentity.fragments.MainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author howard
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                item.setActionView(R.layout.view_refresh);
                ImageView loadingImageView = item.getActionView().findViewById(R.id.loading_image_view);
                Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);
                rotation.setRepeatCount(Animation.INFINITE);
                loadingImageView.startAnimation(rotation);

                Fragment mainFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
                if (mainFragment instanceof MainFragment) {
                    ((MainFragment) mainFragment).getUserProfileFromRefreshClick(item);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
