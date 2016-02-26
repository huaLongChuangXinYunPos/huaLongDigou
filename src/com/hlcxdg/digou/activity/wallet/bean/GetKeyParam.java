package com.hlcxdg.digou.activity.wallet.bean;

public class GetKeyParam {
	public String WServerID="1";// （店铺所在钱包平台ID）
	public String cStoreNo;
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
