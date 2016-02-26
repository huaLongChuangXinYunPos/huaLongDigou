package com.hlcxdg.digou.bean;

import java.math.BigDecimal;

public class SaleSheetDetail {

	public String dSaleDate = "";
	public String cSaleSheetno = "";
	public int iSeed = 1;
	public String cGoodsNo = "";

	// public String cBarCode = "";
	public BigDecimal fPrice = BigDecimal.valueOf(0.0);
//	public BigDecimal bVipPrice = BigDecimal.valueOf(0.0);
	public BigDecimal fVipPrice = BigDecimal.valueOf(0.0);
	// public String bVipRate = "";
	// public String fVipRate = "";
	public BigDecimal fQuantity = BigDecimal.valueOf(0.0);
	// public BigDecimal fAgio = "";
	public BigDecimal fLastMoney = BigDecimal.valueOf(0.0);
	public String cVipNo = "";
	public String cWHno = "";
	public BigDecimal fVipScore_cur = BigDecimal.valueOf(0.0);
	public String fPriceType = "";// ZHINENGLIANGWEI
	// public String bSent;
	public String cGoodsName = "";//
	public String MinIMG0 = "";//
	public String MinIMG1 = "";//

	
	
	
	
	public String getMinIMG1() {
		return MinIMG1;
	}

	public void setMinIMG1(String minIMG1) {
		MinIMG1 = minIMG1;
	}

	public String getMinIMG0() {
		return MinIMG0;
	}

	public void setMinIMG0(String minIMG0) {
		MinIMG0 = minIMG0;
	}

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

	public int getiSeed() {
		return iSeed;
	}

	public void setiSeed(int iSeed) {
		this.iSeed = iSeed;
	}

	public String getcGoodsNo() {
		return cGoodsNo;
	}

	public void setcGoodsNo(String cGoodsNo) {
		this.cGoodsNo = cGoodsNo;
	}

	public String getcGoodsName() {
		return cGoodsName;
	}

	public void setcGoodsName(String cGoodsName) {
		this.cGoodsName = cGoodsName;
	}

	public BigDecimal getfPrice() {
		return fPrice;
	}

	public void setfPrice(BigDecimal fPrice) {
		this.fPrice = fPrice;
	}

//	public BigDecimal getbVipPrice() {
//		return bVipPrice;
//	}
//
//	public void setbVipPrice(BigDecimal bVipPrice) {
//		this.bVipPrice = bVipPrice;
//	}

	public BigDecimal getfVipPrice() {
		return fVipPrice;
	}

	public void setfVipPrice(BigDecimal fVipPrice) {
		this.fVipPrice = fVipPrice;
	}

	public BigDecimal getfQuantity() {
		return fQuantity;
	}

	public void setfQuantity(BigDecimal fQuantity) {
		this.fQuantity = fQuantity;
	}

	public BigDecimal getfLastMoney() {
		return fLastMoney;
	}

	public void setfLastMoney(BigDecimal fLastMoney) {
		this.fLastMoney = fLastMoney;
	}

	public String getcVipNo() {
		return cVipNo;
	}

	public void setcVipNo(String cVipNo) {
		this.cVipNo = cVipNo;
	}

	public String getcWHno() {
		return cWHno;
	}

	public void setcWHno(String cWHno) {
		this.cWHno = cWHno;
	}

	public BigDecimal getfVipScore_cur() {
		return fVipScore_cur;
	}

	public void setfVipScore_cur(BigDecimal fVipScore_cur) {
		this.fVipScore_cur = fVipScore_cur;
	}

	public String getfPriceType() {
		return fPriceType;
	}

	public void setfPriceType(String fPriceType) {
		this.fPriceType = fPriceType;
	}

}
