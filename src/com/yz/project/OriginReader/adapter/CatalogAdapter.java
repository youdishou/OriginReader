package com.yz.project.OriginReader.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yz.project.OriginReader.R;
import com.yz.project.OriginReader.domain.CatalogInfo;

public class CatalogAdapter extends BaseAdapter{
	private List<CatalogInfo> mData;
	private LayoutInflater mInflater;
	
	public CatalogAdapter(List<CatalogInfo> data,Context c){
		mData = data;
		mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_catalog, null);
		}
		((TextView)convertView).setText(mData.get(position).name);
		return convertView;
	}

}
