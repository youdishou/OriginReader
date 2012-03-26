package com.yz.project.OriginReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yz.project.OriginReader.adapter.CatalogAdapter;
import com.yz.project.OriginReader.constant.Catalog;
import com.yz.project.OriginReader.daqindiguo.R;
import com.yz.project.OriginReader.domain.CatalogInfo;

public class CatalogActivity extends YZActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.catalog);
		
		ListView lv = (ListView) findViewById(R.id.lv_catalog);
		lv.setAdapter(new CatalogAdapter(Catalog.getCatalog(), this));
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CatalogInfo ci = (CatalogInfo) parent.getItemAtPosition(position);
				Intent intent = new Intent(CatalogActivity.this,OriginReaderActivity.class);
				intent.putExtra("offset", 0);
				intent.putExtra("fileName", ci.fileName);
				intent.putExtra("h", getIntent().getIntExtra("h", -1));
				intent.putExtra("w", getIntent().getIntExtra("w", -1));
				startActivity(intent);
				finish();
			}
		});
	}
}
