package com.test.shop.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;

import com.test.shop.utils.SharedPrefrence;

/**
 * Created by ashishthakur on 11/2/16.
 */
public class StartActivity extends BaseActivity {
    ActionBar mActionBar;
    private ImageView mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        addActionBar(false, true);
        titleActionBarTextView.setVisibility(View.GONE);
        mSubmit = (ImageView) findViewById(R.id.go);
        if (SharedPrefrence.getInstance(this).readBooleanPrefs(SharedPrefrence.IS_LOGIN)) {
            startActivity(new Intent(getApplicationContext(), ProductListActivity.class));
        }
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });

    }


}
