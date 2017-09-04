package com.test.oschina.mvcproject.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.test.oschina.mvcproject.R;
import com.test.oschina.mvcproject.adapter.ShopAdapter;
import com.test.oschina.mvcproject.callback.ClothAddCallback;
import com.test.oschina.mvcproject.entity.Info;
import com.test.oschina.mvcproject.model.InfoDao;
import com.test.oschina.mvcproject.model.InfoImpl;
import com.test.oschina.mvcproject.view.AnimaoUtils;
import com.test.oschina.mvcproject.view.MoveImageView;

import java.util.ArrayList;

import java.util.List;

public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ListView lv_test;
    View view;
    ShopAdapter adapter;
    List<Info> list = new ArrayList<>();
    InfoDao dao;
    private ImageView holdCart;
    private RelativeLayout holdRootView;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        initView();
        return view;
    }

    private void initView() {
        lv_test = (ListView) view.findViewById(R.id.lv_test);
        holdCart = (ImageView) getActivity().findViewById(R.id.im_shop_view);
        holdRootView = (RelativeLayout) getActivity().findViewById(R.id.container);
        iniData();
        adapter = new ShopAdapter(list, getActivity(), new ClothAddCallback() {
            @Override
            public void updateRedDot(ImageView clothIcon, int position) {
                AnimaoUtils.addCloth(
                        clothIcon, getActivity(), holdRootView, holdCart);
            }
        }, lv_test);
        lv_test.setAdapter(adapter);
    }
    private void iniData() {
        dao = new InfoImpl();
        list = dao.addInfo(new Info());
    }


}
