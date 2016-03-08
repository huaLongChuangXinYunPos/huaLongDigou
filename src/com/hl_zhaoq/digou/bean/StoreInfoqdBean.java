package com.hl_zhaoq.digou.bean;

import java.math.BigDecimal;

public class StoreInfoqdBean {

	private String qdID;
	private String usernameqd = "zhangsan";
	private String storeinfoqdid = "f";
	private String storenameqd = "f";
	private String storeaddrqd = "市区";
	private String storeqdGoodsname = "f";
	private String zhuangtai;
	private BigDecimal storeqdprice = new BigDecimal(22).setScale(2,
			BigDecimal.ROUND_UP);
	private int storeqdnum = 1;
	private double jingdu = 110;
	private double weidu = 40;

	public String getStoreinfoqdid() {
		return storeinfoqdid;
	}

	public void setStoreinfoqdid(String storeinfoqdid) {
		this.storeinfoqdid = storeinfoqdid;
	}

	public String getStorenameqd() {
		return storenameqd;
	}

	public void setStorenameqd(String storenameqd) {
		this.storenameqd = storenameqd;
	}

	public String getStoreaddrqd() {
		return storeaddrqd;
	}

	public void setStoreaddrqd(String storeaddrqd) {
		this.storeaddrqd = storeaddrqd;
	}

	public String getStoreqdGoodsname() {
		return storeqdGoodsname;
	}

	public void setStoreqdGoodsname(String storeqdGoodsname) {
		this.storeqdGoodsname = storeqdGoodsname;
	}

	public double getJingdu() {
		return jingdu;
	}

	public void setJingdu(double jingdu) {
		this.jingdu = jingdu;
	}

	public double getWeidu() {
		return weidu;
	}

	public void setWeidu(double weidu) {
		this.weidu = weidu;
	}

	public String getUsernameqd() {
		return usernameqd;
	}

	public void setUsernameqd(String usernameqd) {
		this.usernameqd = usernameqd;
	}

	public String getQdID() {
		return qdID;
	}

	public void setQdID(String qdID) {
		this.qdID = qdID;
	}

	public BigDecimal getStoreqdprice() {
		return storeqdprice;
	}

	public void setStoreqdprice(BigDecimal storeqdprice) {
		this.storeqdprice = storeqdprice;
	}

	public int getStoreqdnum() {
		return storeqdnum;
	}

	public void setStoreqdnum(int storeqdnum) {
		this.storeqdnum = storeqdnum;
	}

	public String getZhuangtai() {
		return zhuangtai;
	}

	public void setZhuangtai(String zhuangtai) {
		this.zhuangtai = zhuangtai;
	}

}
