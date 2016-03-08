package com.hl_zhaoq.digou.bean;

import java.math.BigDecimal;

public class Jiesuan {
	public String dSaleDate = "";
	public String cSaleSheetno = "";
	public String userNo = "";

	public BigDecimal orderMoney = BigDecimal.valueOf(0.0);
	public BigDecimal couponMoney = BigDecimal.valueOf(0.0);
	public BigDecimal manjianMoney = BigDecimal.valueOf(0.0);

	public BigDecimal fLastMoney = BigDecimal.valueOf(0.0);

	public String getdSaleDate() {
		return dSaleDate;
	}

	public void setdSaleDate(String dSaleDate) {
		this.dSaleDate = dSaleDate;
	}

	public String getcSaleSheetno() {
		return cSaleSheetno;
	}

	public void setcSaleSheetno(String cSaleSheetno) {
		this.cSaleSheetno = cSaleSheetno;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public BigDecimal getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}

	public BigDecimal getManjianMoney() {
		return manjianMoney;
	}

	public void setManjianMoney(BigDecimal manjianMoney) {
		this.manjianMoney = manjianMoney;
	}

	public BigDecimal getfLastMoney() {
		return fLastMoney;
	}

	public void setfLastMoney(BigDecimal fLastMoney) {
		this.fLastMoney = fLastMoney;
	}

}
