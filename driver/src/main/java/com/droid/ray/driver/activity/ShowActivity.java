package com.droid.ray.driver.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.droid.ray.driver.R;

public class ShowActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        try {

            Fragment fragment = (Fragment) Class.forName(getIntent().getStringExtra("fragment")).newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_container, fragment)
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
