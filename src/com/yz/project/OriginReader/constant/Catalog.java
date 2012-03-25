package com.yz.project.OriginReader.constant;

import java.util.ArrayList;
import java.util.List;

import com.yz.project.OriginReader.domain.CatalogInfo;

public class Catalog {
	public final static List<CatalogInfo> getCatalog(){
		List<CatalogInfo> catalog = new ArrayList<CatalogInfo>();

		catalog.add(new CatalogInfo("序言", "xy"));
		catalog.add(new CatalogInfo("黑色裂变", "hslb"));
		catalog.add(new CatalogInfo("国命纵横", "gmzh"));
		catalog.add(new CatalogInfo("金戈铁马", "jgtm"));
		catalog.add(new CatalogInfo("阳谋春秋", "ymcq"));
		catalog.add(new CatalogInfo("铁血文明", "txwm"));
		catalog.add(new CatalogInfo("帝国烽烟", "dgfy"));
		
		return catalog;
	}
}
