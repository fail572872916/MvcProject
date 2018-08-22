package com.test.oschina.mvcproject.checkerboard;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.test.oschina.mvcproject.R;
import com.test.oschina.mvcproject.entity.DetailPoint;
import com.test.oschina.mvcproject.entity.gen.DetailPointDao;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.test.oschina.mvcproject.MyApplication.pointDao;

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

                DetailPoint detailPoint = new DetailPoint();
                detailPoint.setCol(1);
                detailPoint.setRol(1);
                detailPoint.setXValue(point.x);
                detailPoint.setYValue(point.y);
                long end = pointDao.insert(detailPoint);
                String msg = "";
                if (end > 0) {
                    msg = "001新增成功~";
                } else {
                    msg = "新增失败~";
                }

                Toast.makeText(LatticeActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

        });
    }

}
