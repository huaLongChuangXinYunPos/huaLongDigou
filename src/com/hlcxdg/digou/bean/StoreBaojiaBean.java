package com.hlcxdg.digou.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class StoreBaojiaBean {
	private String baojiaID = "fd12323";
	private String userNeedsID = "20151106095057-1";
	private String storeNo = "12323";
	private String storeName ;
	private String userNo = "23823872";
	private String chengjiao = "";
	private BigDecimal storebaojia = new BigDecimal(22);
	private float sdistance = 232.3f;
	private Timestamp sdatetime;
	private String peisongTel = "";
	private String goodsName;
	private float jingdu = 116.323f;
	private float weidu = 40.323f;
	private String peisongName = "";

	public float getJingdu() {
		return jingdu;
	}

	public void setJingdu(float jingdu) {
		this.jingdu = jingdu;
	}

	public float getWeidu() {
		return weidu;
	}

	public void setWeidu(float weidu) {
		this.weidu = weidu;
	}

	public String getPeisongName() {
		return peisongName;
	}

	public void setPeisongName(String peisongName) {
		this.peisongName = peisongName;
	}

	public StoreBaojiaBean() {
		super();
	}

	public StoreBaojiaBean(String baojiaID, String userNeedsID, String storeNo,
			String userNo, String chengjiao, BigDecimal storebaojia,
			float sdistance, Timestamp sdatetime) {
		super();
		this.baojiaID = baojiaID;
		this.userNeedsID = userNeedsID;
		this.storeNo = storeNo;
		this.userNo = userNo;
		this.chengjiao = chengjiao;
		this.storebaojia = storebaojia;
		this.sdistance = sdistance;
		this.sdatetime = sdatetime;
	}

	public String getBaojiaID() {
		return baojiaID;
	}

	public void setBaojiaID(String baojiaID) {
		this.baojiaID = baojiaID;
	}

	public String getUserNeedsID() {
		return userNeedsID;
	}

	public void setUserNeedsID(String userNeedsID) {
		this.userNeedsID = userNeedsID;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public BigDecimal getStorebaojia() {
		return storebaojia;
	}

	public void setStorebaojia(BigDecimal storebaojia) {
		this.storebaojia = storebaojia;
	}

	public float getSdistance() {
		return sdistance;
	}

	public void setSdistance(float sdistance) {
		this.sdistance = sdistance;
	}

	public Timestamp getSdatetime() {
		return sdatetime;
	}

	public void setSdatetime(Timestamp sdatetime) {
		this.sdatetime = sdatetime;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setChengjiao(String chengjiao) {
		this.chengjiao = chengjiao;
	}

	public String getChengjiao() {
		return chengjiao;
	}

	public String getPeisongTel() {
		return peisongTel;
	}

	public void setPeisongTel(String peisongTel) {
		this.peisongTel = peisongTel;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

}
