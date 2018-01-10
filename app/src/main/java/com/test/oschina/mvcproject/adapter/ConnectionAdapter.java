package com.test.oschina.mvcproject.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.test.oschina.mvcproject.R;
import com.test.oschina.mvcproject.callback.SelectedItem;
import com.test.oschina.mvcproject.utils.Tools;

public class ConnectionAdapter extends BaseAdapter {
	private String TAG;
	private final Context context;
	// 组件
	private ViewHolder holder;
	// 常量
	private final int unit;
	private final int[] display;
	private ListView.LayoutParams listParams;
	private SelectedItem selectedIndex;
	// 变量
	private List<Map<String, String>> listAddress;

	public ConnectionAdapter(Context context, List<Map<String, String>> listAddress, SelectedItem selectedIndex) {
		this.TAG = getClass().getSimpleName();
		this.context = context;
		this.unit = TypedValue.COMPLEX_UNIT_PX;
		this.display = new Tools().getDisplayByPx(context);
		this.listAddress = listAddress;
		this.selectedIndex = selectedIndex;
	}

	@Override
	public int getCount() {
		return listAddress.size();
	}

	@Override
	public Object getItem(int position) {
		return listAddress.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup vg) {
		Map<String, String> item = listAddress.get(position);
		if (null == view) {
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.item_connection, vg, false);
			listParams = (LayoutParams) view.getLayoutParams();
			listParams.height = display[1] / 8;
			view.setLayoutParams(listParams);
			holder.ivRemind = (ImageView) view.findViewById(R.id.item_connection_iv_remind);
			holder.tvName = (TextView) view.findViewById(R.id.item_connection_tv_name);
			holder.tvName.setTextSize(unit, display[1] / 10 / 2);
			holder.ivDelete = (ImageView) view.findViewById(R.id.item_connection_iv_delete);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		if (item.get("msg") != null) {
			holder.ivRemind.setVisibility(View.VISIBLE);
		} else {
			holder.ivRemind.setVisibility(View.INVISIBLE);
		}
		holder.tvName.setText(item.get("address"));
		holder.ivDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectedIndex.result(position);
			}
		});
		return view;
	}

	// 刷新数据
	public void notifyDataSetChanged(List<Map<String, String>> listAddress) {
		this.listAddress = listAddress;
		Log.d("ConnectionAdapter", listAddress.toString());
		this.notifyDataSetChanged();

	}

	// ViewHolder
	final class ViewHolder {
		private ImageView ivRemind;
		private TextView tvName;
		private ImageView ivDelete;
	}

}
