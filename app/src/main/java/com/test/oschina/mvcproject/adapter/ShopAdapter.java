package com.test.oschina.mvcproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.oschina.mvcproject.R;
import com.test.oschina.mvcproject.callback.ClothAddCallback;
import com.test.oschina.mvcproject.entity.Info;
import com.test.oschina.mvcproject.utils.CommonUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 * 商品适配器
 */

public class ShopAdapter extends BaseAdapter {

    List<Info> list;
    private Context mConText;
    private ClothAddCallback callback;
    private ListView listView;
    public ShopAdapter(List<Info> list, Context mConText, ClothAddCallback callback, ListView listView) {
        this.list = list;
        this.mConText = mConText;
        this.callback = callback;
        this.listView = listView;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mConText);
            view = mInflater.inflate(R.layout.item_lv_shop, null);
            viewHolder.tv_iem_money = (TextView) view.findViewById(R.id.tv_iem_money);
            viewHolder.im_iem_iamge = (ImageView) view.findViewById(R.id.im_iem_iamge);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.im_iem_iamge.setImageResource(R.mipmap.ic_launcher);
        viewHolder.tv_iem_money.setText(list.get(i).getText() + "\n" + list.get(i).getMoney());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (CommonUtils.isFastDoubleClick()) {
                    Toast.makeText(mConText, "请慢点", Toast.LENGTH_SHORT).show();
                    return;
                } else {


                    callback.updateRedDot(viewHolder.im_iem_iamge, i);
                    notifyDataSetChanged();
                }

            }
        });

        return view;
    }

    private class ViewHolder {
        private TextView tv_iem_money;
        private ImageView im_iem_iamge;
    }

}
