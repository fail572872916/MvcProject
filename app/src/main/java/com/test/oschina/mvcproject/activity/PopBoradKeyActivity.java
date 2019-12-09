package com.test.oschina.mvcproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.test.oschina.mvcproject.R;
import com.test.oschina.mvcproject.utils.AlertDialogUtil;

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
