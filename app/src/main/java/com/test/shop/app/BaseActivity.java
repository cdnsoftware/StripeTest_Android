package com.test.shop.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.test.shop.utils.AppConstant;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseActivity extends AppCompatActivity implements AppConstant {

    public TextView titleRightButton;
    public TextView titleActionBarTextView;
    private ProgressDialog mProgressDialog;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.please_wait));
    }

    /*
    use for showing toast message
     */
    public void showToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /*
    use for showing progress dialog
     */
    public void showProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    /*
    dismis  progress dialog that is  showing.
     */
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    /*
     get edittext containt and convert to string
      */
    public String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * validating email id
     */

    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /*
     validating password with retype password
      */
    public boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

    /*
    chack valid
     */
    public boolean isFieldNotBlank(String mNames, String message) {
        if (mNames.length() <= 0) {
            if (message != null)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*
    This method is using for facebook login.
     */
    public void fbLogin() {
        try {
            LoginManager.getInstance()
                    .logInWithReadPermissions(
                            ((Activity) this),
                            Arrays.asList("user_friends", "public_profile",
                                    "email"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     Add action bar on which screen that will extend BaseActivity class
     */
    public void addActionBar(boolean isBack, boolean isRight) {
        mActionBar = getSupportActionBar();
        ActionBar.LayoutParams layoutParam = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        mActionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mActionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.white));
        View mCustomView = inflator.inflate(R.layout.title_bar, null);
        mCustomView.setLayoutParams(layoutParam);
        titleActionBarTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        ImageView naviImage = (ImageButton) mCustomView.findViewById(R.id.backButton);
        naviImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleRightButton = (TextView) mCustomView.findViewById(R.id.rightButton);
        if (isBack)
            naviImage.setVisibility(View.VISIBLE);
        else
            naviImage.setVisibility(View.GONE);
        if (isRight) {
            titleRightButton.setVisibility(View.VISIBLE);
        } else {
            titleRightButton.setVisibility(View.GONE);
        }

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        Toolbar tool = (Toolbar) mCustomView.getParent();
        tool.setContentInsetsAbsolute(0, 0);
    }

}
