package com.hlcxdg.digou.activity.wallet.bean;

import java.math.BigDecimal;

public class WPubKey {

	public String WServerPubKey;
	public String WUpdateTime;
	public String WStatus; // 不用
	public String WPriKey; // 不用

	public String WServerID; // （店铺所在钱包平台ID）

	public String cStoreNo;
	public String getWServerPubKey() {
		return WServerPubKey;
	}

	public void setWServerPubKey(String wServerPubKey) {
		WServerPubKey = wServerPubKey;
	}

	public String getWUpdateTime() {
		return WUpdateTime;
	}

	public void setWUpdateTime(String wUpdateTime) {
		WUpdateTime = wUpdateTime;
	}

	public String getWStatus() {
		return WStatus;
	}

	public void setWStatus(String wStatus) {
		WStatus = wStatus;
	}

	public String getWPriKey() {
		return WPriKey;
	}

	public void setWPriKey(String wPriKey) {
		WPriKey = wPriKey;
	}

	public String BuyerName; // （支付方名称）

	public String getWServerID() {
		return WServerID;
	}

	public void setWServerID(String wServerID) {
		WServerID = wServerID;
	}

	public String getcStoreNo() {
		return cStoreNo;
	}

	public void setcStoreNo(String cStoreNo) {
		this.cStoreNo = cStoreNo;
	}

	public String getBuyerName() {
		return BuyerName;
	}

	public void setBuyerName(String buyerName) {
		BuyerName = buyerName;
	}

}
