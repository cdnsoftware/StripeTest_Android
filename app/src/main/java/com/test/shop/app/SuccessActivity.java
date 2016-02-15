package com.test.shop.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.test.shop.utils.Utils;


public class SuccessActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        this.setFinishOnTouchOutside(false);
        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.backButton).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logout) {
            Utils.logout(this);
        } else if (v.getId() == R.id.backButton) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (ProductOrderActivity.instance != null) {
            ProductOrderActivity.instance.finish();
        }
        super.onBackPressed();
    }
}
