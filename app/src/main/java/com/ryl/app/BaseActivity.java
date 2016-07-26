package com.ryl.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

/**
 * Created by ryl on 2016/7/25.
 */
public class BaseActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {

    }
}
