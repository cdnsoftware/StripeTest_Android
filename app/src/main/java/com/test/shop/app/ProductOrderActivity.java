package com.test.shop.app;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.test.shop.database.DbHelper;
import com.test.shop.model.PaymentInformationBean;
import com.test.shop.utils.SharedPrefrence;
import com.test.shop.utils.Utils;

import java.util.Calendar;

/**
 * ProductOrderActivity class use for ordering the perticular product and that class responsible for doing the payment
 */
public class ProductOrderActivity extends BaseActivity {
    public static ProductOrderActivity instance;
    ActionBar mActionBar;
    EditText mName, mLastname, mCity, mState, mCountry, mComment, mAddressLineOne, mAddressLineTwo, mZipCOde,
            mCardNumber, mCvvNumber;
    TextView mExpirationDate;
    RelativeLayout mPaymentRupees;
    int mMonth;
    int mYear;
    int mDay;
    String expireDateString;
    Context mContext;
    PaymentInformationBean paymentInformationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_order);
        instance = this;
        mContext = this;
        addActionBar(true, false);
        titleActionBarTextView.setText(getString(R.string.title_activity_product_order));
        setCasting();
        setPreviousInformation();
        mPaymentRupees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setValidation();
            }
        });

    }

    //populate privious data
    private void setPreviousInformation() {
        String email = SharedPrefrence.getInstance(mContext).readPrefs(SharedPrefrence.EMAIL);
        if (!SharedPrefrence.getInstance(mContext).readBooleanPrefs(SharedPrefrence.IS_SOCIAL))
            paymentInformationBean = (PaymentInformationBean) DbHelper.getInstance(this).getPaymentInfo(email.substring(0, email.indexOf("@")));

        else
            paymentInformationBean = (PaymentInformationBean) DbHelper.getInstance(this).getPaymentInfo(SharedPrefrence.getInstance(mContext).readPrefs(SharedPrefrence.ID));

        if (paymentInformationBean != null) {
            if (paymentInformationBean.getNumber() != null)
                mCardNumber.setText(paymentInformationBean.getNumber());
            if (paymentInformationBean.getCvc() != null)
                mCvvNumber.setText(paymentInformationBean.getCvc());
            if (paymentInformationBean.getFirstName() != null)
                mName.setText(paymentInformationBean.getFirstName());
            if (paymentInformationBean.getLastName() != null)
                mLastname.setText(paymentInformationBean.getLastName());
            if (paymentInformationBean.getAddressLine1() != null)
                mAddressLineOne.setText(paymentInformationBean.getAddressLine1());
            if (paymentInformationBean.getAddressLine2() != null)
                mAddressLineTwo.setText(paymentInformationBean.getAddressLine2());
            if (paymentInformationBean.getAddressCity() != null)
                mCity.setText(paymentInformationBean.getAddressCity());
            if (paymentInformationBean.getAddressState() != null)
                mState.setText(paymentInformationBean.getAddressState());
            if (paymentInformationBean.getAddressCountry() != null)
                mCountry.setText(paymentInformationBean.getAddressCountry());
            if (paymentInformationBean.getExpireDate() != null)
                mExpirationDate.setText(paymentInformationBean.getExpireDate());
            mMonth = paymentInformationBean.getExpMonth();
            mYear = paymentInformationBean.getExpYear();
            if (paymentInformationBean.getAddressZip() != null)
                mZipCOde.setText(paymentInformationBean.getAddressZip());

        }
    }

    /**
     * init  those view which are using in ProductOrderActivity class .
     */
    public void setCasting() {
        mName = (EditText) findViewById(R.id.mFirstName);
        mLastname = (EditText) findViewById(R.id.mLastname);
        mCity = (EditText) findViewById(R.id.mCity);
        mState = (EditText) findViewById(R.id.mState);
        mCountry = (EditText) findViewById(R.id.mCountry);
        mCardNumber = (EditText) findViewById(R.id.mCardnumber);
        mCvvNumber = (EditText) findViewById(R.id.mCvvnumber);
        mAddressLineOne = (EditText) findViewById(R.id.mAddresLineOne);
        mAddressLineTwo = (EditText) findViewById(R.id.mAddresLineTwo);
        mZipCOde = (EditText) findViewById(R.id.mZipCode);
        mExpirationDate = (TextView) findViewById(R.id.mExpirationDate);
        mComment = (EditText) findViewById(R.id.mComment);
        mPaymentRupees = (RelativeLayout) findViewById(R.id.totalPayment);
        datepicker();
    }

    /**
     * This method is responsible for saving  a credit card information with swipe intigration
     */

    public void saveCreditCard() {
        showProgressDialog();
        Card card = new Card(getText(mCardNumber), mMonth, mYear,
                getText(mCvvNumber), getText(mName) + " " + getText(mLastname),
                getText(mAddressLineOne), getText(mAddressLineTwo),
                getText(mCity), getText(mState), "", getText(mCountry),
                "USD");
        boolean validation = card.validateCard();
        if (validation) {
            new Stripe().createToken(
                    card,
                    PUBLISHABLE_KEY,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            //  showToastMsg(getString(R.string.paymentSuccess));
                            hideProgressDialog();
                            if (paymentInformationBean == null)
                                paymentInformationBean = new PaymentInformationBean();
                            if (!SharedPrefrence.getInstance(mContext).readBooleanPrefs(SharedPrefrence.IS_SOCIAL))
                                paymentInformationBean.setEmail(SharedPrefrence.getInstance(mContext).readPrefs(SharedPrefrence.EMAIL));
                            else
                                paymentInformationBean.setSocial_id(SharedPrefrence.getInstance(mContext).readPrefs(SharedPrefrence.ID));
                            paymentInformationBean.setFirstName(getText(mName));
                            paymentInformationBean.setLastName(getText(mLastname));
                            paymentInformationBean.setCvc(getText(mCvvNumber));
                            paymentInformationBean.setAddressCity(getText(mCity));
                            paymentInformationBean.setAddressLine1(getText(mAddressLineOne));
                            paymentInformationBean.setAddressLine2(getText(mAddressLineTwo));
                            paymentInformationBean.setAddressState(getText(mState));
                            paymentInformationBean.setAddressCountry(getText(mCountry));
                            paymentInformationBean.setNumber(getText(mCardNumber));
                            paymentInformationBean.setExpireDate(expireDateString);
                            paymentInformationBean.setExpMonth(mMonth);
                            paymentInformationBean.setExpYear(mYear);
                            paymentInformationBean.setAddressZip(getText(mZipCOde));
                            String email = SharedPrefrence.getInstance(mContext).readPrefs(SharedPrefrence.EMAIL);
                            if (!SharedPrefrence.getInstance(mContext).readBooleanPrefs(SharedPrefrence.IS_SOCIAL))
                                paymentInformationBean.setUserName(email.substring(0, email.indexOf("@")));
                            else
                                paymentInformationBean.setUserName(SharedPrefrence.getInstance(mContext).readPrefs(SharedPrefrence.ID));
                            DbHelper.getInstance(mContext).insertPaymentInfo(paymentInformationBean);
                            startActivity(new Intent(getApplicationContext(), SuccessActivity.class));

                        }

                        public void onError(Exception error) {
                            hideProgressDialog();
                            showToastMsg(error.getLocalizedMessage());

                        }
                    });
        } else if (!card.validateNumber()) {
            showToastMsg(getString(R.string.validationCard1));
            hideProgressDialog();
        } else if (!card.validateExpiryDate()) {
            showToastMsg(getString(R.string.validationCard2));
            hideProgressDialog();
        } else if (!card.validateCVC()) {
            showToastMsg(getString(R.string.validationCard3));
            hideProgressDialog();
        } else {
            showToastMsg(getString(R.string.validationCard4));
            hideProgressDialog();
        }


    }
// use for show date picker and add expiration date

    void datepicker() {
        mExpirationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dpd = new DatePickerDialog(ProductOrderActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // Display Selected date in textbox
                                    mExpirationDate.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);
                                    expireDateString = dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year;
                                    mYear = year;
                                    mMonth = monthOfYear;
                                    mDay = dayOfMonth;
                                }
                            }, mYear, mMonth, mDay);
                    dpd.show();

                }
            }
        });

    }

    /**
     *
     */
    void setValidation() {

        final String name = mName.getText().toString();
        final String lastname = mLastname.getText().toString();
        final String cardnumber = mCardNumber.getText().toString();
        final String cvvnumber = mCvvNumber.getText().toString();
        final String address = mAddressLineOne.getText().toString();
        final String zipcode = mZipCOde.getText().toString();
        final String city = mCity.getText().toString();
        final String state = mState.getText().toString();
        final String country = mCountry.getText().toString();

        if (!isFieldNotBlank(name, null)) {
            mName.setError(getText(R.string.nameRequired));
        } else if (!isFieldNotBlank(lastname, null)) {
            mLastname.setError(getText(R.string.lastnameRequired));
        } else if (!Utils.isValidCardNumber(cardnumber)) {
            mCardNumber.setError(getText(R.string.cardNo_required));
        } else if (!Utils.isValidCvvNumber(cvvnumber)) {
            mCvvNumber.setError(getText(R.string.cvv_requried));
        } else if (!isFieldNotBlank(address, null)) {
            mAddressLineOne.setError(getText(R.string.enter_address));
        } else if (!isFieldNotBlank(city, null)) {
            mCity.setError(getText(R.string.enter_city));
        } else if (!isFieldNotBlank(state, null)) {
            mState.setError(getText(R.string.enter_state));
        } else if (!isFieldNotBlank(zipcode, null)) {
            mZipCOde.setError(getText(R.string.enter_zip));
        } else if (!isFieldNotBlank(country, null)) {
            mCountry.setError(getText(R.string.enter_country));
        } else {
            saveCreditCard();


        }


    }

}
