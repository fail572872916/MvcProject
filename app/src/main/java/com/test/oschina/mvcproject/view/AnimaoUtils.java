package com.test.oschina.mvcproject.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.test.oschina.mvcproject.R;

/**
 * Created by Administrator on 2017/9/4.
 * 动画工具类
 */

public class AnimaoUtils {
    //添加购物车动画
    public static void addCloth(View clothIcon , final Context mContext, final ViewGroup holdRootView, final  View holdCart) {
        int[] childCoordinate = new int[2];
        int[] parentCoordinate = new int[2];
        int[] shopCoordinate = new int[2];

        //1.分别获取被点击View、父布局、购物车在屏幕上的坐标xy。
        clothIcon.getLocationInWindow(childCoordinate);
        holdRootView.getLocationInWindow(parentCoordinate);
        holdCart.getLocationInWindow(shopCoordinate);
        //2.自定义ImageView 继承ImageView
        final MoveImageView img = new MoveImageView(mContext);
        clothIcon.setDrawingCacheEnabled(true);
        img.setImageBitmap(Bitmap.createBitmap(clothIcon.getDrawingCache()));

        clothIcon.setDrawingCacheEnabled(false);
        //3.设置img在父布局中的坐标位置
        img.setX(childCoordinate[0] - parentCoordinate[0]);
        img.setY(childCoordinate[1] - parentCoordinate[1]);
        //4.父布局添加该Img

        holdRootView.addView(img);

        //5.利用 二次贝塞尔曲线 需首先计算出 MoveImageView的2个数据点和一个控制点
        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();
        //开始的数据点坐标就是 addV的坐标
        startP.x = childCoordinate[0] - parentCoordinate[0];
        startP.y = childCoordinate[1] - parentCoordinate[1];
        //结束的数据点坐标就是 shopImg的坐标
        endP.x = shopCoordinate[0] - parentCoordinate[0];
        endP.y = shopCoordinate[1] - parentCoordinate[1];
        //控制点坐标 x等于 购物车x；y等于 addV的y
        controlP.x = endP.x;
        controlP.y = startP.y;
        //启动属性动画
        ObjectAnimator animator = ObjectAnimator.ofObject(img, "mPointF",
                new MoveImageView.PointFTypeEvaluator(controlP), startP, endP);

        animator.setDuration(2000);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0.5f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                0.5f, 0.5f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                0.5f, 0.5f);
        ObjectAnimator.ofPropertyValuesHolder(img, pvhX, pvhY,pvhZ).setDuration(2000).start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束后 父布局移除 img
                Object target = ((ObjectAnimator) animation).getTarget();
                holdRootView.removeView((View) target);
                //购物车 开始一个放大动画
                Animation scaleAnim = AnimationUtils.loadAnimation(mContext, R.anim.cart_scale);
                holdCart.startAnimation(scaleAnim);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
}
