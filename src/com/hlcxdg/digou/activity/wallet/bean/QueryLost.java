package com.hlcxdg.digou.activity.wallet.bean;

import java.math.BigDecimal;

public class QueryLost {
	public String WServerID;
	public String BuyerName;
	// "WServerID":"1","BuyerName":"222","WMoney":0.0000
	public BigDecimal WMoney = new BigDecimal(0);

	public BigDecimal getMyWMoney() {
		return WMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getWMoney() {
		return WMoney;
	}

	public void setWMoney(BigDecimal wMoney) {
		WMoney = wMoney;
	}

	public String getWServerID() {
		return WServerID;
	}

	public void setWServerID(String wServerID) {
		WServerID = wServerID;
	}

	public String getBuyerName() {
		return BuyerName;
	}

	public void setBuyerName(String buyerName) {
		BuyerName = buyerName;
	}

}
