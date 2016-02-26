package com.hlcxdg.digou.bean;

import java.math.BigDecimal;

public class UserCouponYouhq {
	
	private int userNo;
	private int Coupon_ID;
	private String cSaleDate;
	private String cSaleSheetno;
	private String Status;
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public int getCoupon_ID() {
		return Coupon_ID;
	}
	public void setCoupon_ID(int coupon_ID) {
		Coupon_ID = coupon_ID;
	}
	public String getcSaleDate() {
		return cSaleDate;
	}
	public void setcSaleDate(String cSaleDate) {
		this.cSaleDate = cSaleDate;
	}
	public String getcSaleSheetno() {
		return cSaleSheetno;
	}
	public void setcSaleSheetno(String cSaleSheetno) {
		this.cSaleSheetno = cSaleSheetno;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	
}
