package com.yz.project.OriginReader.constant;

import java.util.ArrayList;
import java.util.List;

import com.yz.project.OriginReader.domain.CatalogInfo;

public class Catalog {
	public final static List<CatalogInfo> getCatalog(){
		List<CatalogInfo> catalog = new ArrayList<CatalogInfo>();

		catalog.add(new CatalogInfo("序言", "xy.jpg"));
		catalog.add(new CatalogInfo("黑色裂变", "hslb.jpg"));
		catalog.add(new CatalogInfo("国命纵横", "gmzh.jpg"));
		catalog.add(new CatalogInfo("金戈铁马", "jgtm.jpg"));
		catalog.add(new CatalogInfo("阳谋春秋", "ymcq.jpg"));
		catalog.add(new CatalogInfo("铁血文明", "txwm.jpg"));
		catalog.add(new CatalogInfo("帝国烽烟", "dgfy.jpg"));
		
		return catalog;
	}
}
