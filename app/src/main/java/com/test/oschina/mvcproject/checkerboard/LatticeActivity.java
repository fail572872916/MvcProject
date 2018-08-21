package com.test.oschina.mvcproject.checkerboard;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.test.oschina.mvcproject.R;

import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LatticeActivity extends AppCompatActivity {
    @BindView(R.id.lattice_view)
    com.test.oschina.mvcproject.checkerboard.lattice latticeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lattice);
        ButterKnife.bind(this);
        latticeView.setOnViewClick(new lattice.OnViewClick() {
            @Override
            public void onClick(Point point) {
                Log.d("LatticeActivity", "point:" + point);
            }

        });
    }

}
