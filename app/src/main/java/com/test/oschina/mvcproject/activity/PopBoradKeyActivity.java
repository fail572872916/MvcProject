package com.test.oschina.mvcproject.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.test.oschina.mvcproject.R;
import com.test.oschina.mvcproject.utils.AlertDialogUtil;
import com.test.oschina.mvcproject.utils.PopWindowUtil;

/**
 * @author Administrator
 * @name MvcProject
 * @class name：com.test.oschina.mvcproject.activity
 * @class describe
 * @time 2017-11-07 17:56
 * @change
 * @chang time
 * @class describe
 */
public class PopBoradKeyActivity extends AppCompatActivity {

    private Button mBtPop;
    private LinearLayout mLinShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pop_broadkey);
        initView();
    }

    private void initView() {
        mBtPop = (Button) findViewById(R.id.tv_pop_show);
        mLinShow = (LinearLayout) findViewById(R.id.lin_show);
        initListener();
    }

    private void initListener() {
        mBtPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogUtil dialogUtilAlertDialogUtil = new AlertDialogUtil(PopBoradKeyActivity.this);
                dialogUtilAlertDialogUtil.setCanceledOnTouchOutside(false);
                dialogUtilAlertDialogUtil.show();
            }
        });
    }
}
