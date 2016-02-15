package com.test.shop.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.test.shop.model.ResponseBase;
import com.test.shop.netcom.CheckNetworkState;
import com.test.shop.netcom.GsonPostRequest;
import com.test.shop.utils.LogUtils;
import com.test.shop.utils.SharedPrefrence;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ashishthakur on 11/2/16.
 */
public class SignInActivity extends BaseActivity {
    private static final String REQUIRED_MSG = "required";
    public static SignInActivity instance;
    ActionBar mActionBar;
    /**
     * facebook login callback listener  in this listener override Three method onCompleted and  onCancel and onError
     * if  login success then will call onCompleted and if cancel then call inCancel if login fail then call onError method.
     */
    FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(final LoginResult loginResult) {

            GraphRequest.newMeRequest(loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject user,
                                                GraphResponse graphResponse) {

                            //    if(user.has("id") && user.optString("id")!=null && user.optString("id").length()>0) {
                            LogUtils.v(LogUtils.TAG, user.toString());
                            CallFromFbToProductScreen(user.optString("id"));

                            showToastMsg(getString(R.string.fbloginSuccess));
                /*     }else
                     {
                         showToastMsg(getString(R.string.emailNotConfigure));

                     }*/
                        }
                    }).executeAsync();
        }

        @Override
        public void onCancel() {
            showToastMsg(getString(R.string.fbloginCancel));


        }

        @Override
        public void onError(FacebookException exception) {
            showToastMsg(getString(R.string.fbloginError));

        }
    };
    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private ImageView mSubmit;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_sign_in);
        addActionBar(true, false);
        titleActionBarTextView.setText(getString(R.string.title_activity_signin));
        mEmail = (EditText) findViewById(R.id.emailSignIn);
        mPassword = (EditText) findViewById(R.id.passwordSignIn);
        mSubmit = (ImageView) findViewById(R.id.signIn);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                facebookCallback);
        initValidationForm();
    }

    /*
    init Sign form field and also call related listeners
     */
    private void initValidationForm() {


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                if (!isValidEmail(email)) {
                    mEmail.setError(getString(R.string.invalidEmail));
                }

                final String pass = mPassword.getText().toString().trim();
                if (!isValidPassword(pass)) {
                    mPassword.setError(getString(R.string.invalidPassword));
                }
                if (isValidEmail(email) && (isValidPassword(pass))) {
                    doLogin(email, pass);
                }
            }
        });
        findViewById(R.id.mFbLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbLogin();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            /**
             * For FaceBook
             */
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * doLogin method  do login with Auth api using Volloy
     *
     * @param username
     * @param password
     */
    private void doLogin(String username, String password) {
        Map<String, String> mParams;
        mParams = new HashMap<String, String>();
        mParams.put(CLIENT_ID, MY_CLIENT_ID);
        mParams.put(USER_NAME, username);
        mParams.put(PASSWORD, password);
        mParams.put(CONNECTION, "Username-Password-Authentication");
        mParams.put(GRANT_TYPE, "password");
        mParams.put(SCOPE, "openid");
        mParams.put(DEVICE, "android");

        if (!CheckNetworkState.isOnline(this)) {
            showToastMsg(getString(R.string.network_error));
            return;
        }
        showProgressDialog();
        String url = REQ_DO_LOGIN;
        GsonPostRequest gsonObjRequest = new GsonPostRequest<ResponseBase>(url, ResponseBase.class, null, mParams, new Response.Listener<ResponseBase>() {

            @Override
            public void onResponse(ResponseBase response) {
                LogUtils.i(LogUtils.TAG, response.toString());
                hideProgressDialog();
                CallToProductScreen(mEmail.getText().toString().trim());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                hideProgressDialog();

                if (error instanceof NetworkError) {

                } else if (error instanceof ClientError) {

                } else if (error instanceof ServerError) {

                } else if (error instanceof AuthFailureError) {
                    showToastMsg(getString(R.string.worng_email));
                } else if (error instanceof ParseError) {

                } else if (error instanceof NoConnectionError) {

                } else if (error instanceof TimeoutError) {

                } else if (error instanceof VolleyError) {

                } else {
                    showToastMsg(getString(R.string.worng_email));
                }
            }
        });

        gsonObjRequest.setTag(LogUtils.TAG);
        Volley.newRequestQueue(this).add(gsonObjRequest);
    }

    /*
    call product screen from auth api login
     */
    public void CallToProductScreen(String email) {
        SharedPrefrence.getInstance(this).writePrefs(SharedPrefrence.EMAIL, email);
        SharedPrefrence.getInstance(this).writeBooleanPrefs(SharedPrefrence.IS_SOCIAL, false);
        SharedPrefrence.getInstance(this).writeBooleanPrefs(SharedPrefrence.IS_LOGIN, true);
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }


    /**
     * call product screen from fb login
     *
     * @param id
     */
    public void CallFromFbToProductScreen(String id) {
        SharedPrefrence.getInstance(this).writePrefs(SharedPrefrence.ID, id);
        SharedPrefrence.getInstance(this).writeBooleanPrefs(SharedPrefrence.IS_SOCIAL, true);
        Intent intent = new Intent(this, ProductListActivity.class);
        SharedPrefrence.getInstance(this).writeBooleanPrefs(SharedPrefrence.IS_LOGIN, true);
        startActivity(intent);

    }

}
