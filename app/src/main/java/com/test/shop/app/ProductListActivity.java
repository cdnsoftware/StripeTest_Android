package com.test.shop.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.test.shop.adapter.ProductAdapter;
import com.test.shop.utils.Utils;

import java.util.ArrayList;
/*
This class is use for showing the list of product if you click any product item will go to the Product Order Screen.
 */

public class ProductListActivity extends BaseActivity {
    public static ProductListActivity instance;
    GridView gridView;
    private ActionBar mActionBar;
    private ProductAdapter mProductAdapter;
    private ArrayList<Integer> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_product_list);
        addActionBar(false, true);
        titleActionBarTextView.setText(getString(R.string.title_activity_product_list));
        titleRightButton.setText(getString(R.string.Logout));
        titleRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.logout(ProductListActivity.this);

            }
        });
        mProductAdapter = new ProductAdapter(this);
        gridView = (GridView) findViewById(R.id.mProducts);
        gridView.setAdapter(mProductAdapter);
        gridItemListener();
    }

    /*
      Product item click listener
     */
    private void gridItemListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                startActivity(new Intent(getApplicationContext(), ProductOrderActivity.class));

            }
        });
    }


    @Override
    public void onBackPressed() {
        Utils.showExitAlert(this);

    }


}
