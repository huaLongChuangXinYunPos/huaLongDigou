package com.hlcxdg.digou.bean;

import java.math.BigDecimal;

public class Goods {
	public String cBarcode;

	public String cCredit;

	public String cLevel;
	public String O_distence;
	public String getO_distence() {
		return O_distence;
	}

	public void setO_distence(String o_distence) {
		O_distence = o_distence;
	}

	public String cAddr;
	public String cStoreNo = "";
	public String cStoreName = "";
	public String cGoodsNo = "";
	public String cGoodsName = "";
	public String cGoodsTypeno = "";
	public String cGoodsTypename = "";
	public String cProductdDate = "";
	public String O_Famousspecialty;
	public String O_PriceFlag;
	public String O_bNew;
	public String MinIMG1;
	public String MinIMG0;
	private BigDecimal fNormalPrice = new BigDecimal("0");
	private BigDecimal fVipPrice = new BigDecimal("0");
	private BigDecimal fVipPrice_student = new BigDecimal("0");
	public String getcBarcode() {
		return cBarcode;
	}

	public void setcBarcode(String cBarcode) {
		this.cBarcode = cBarcode;
	}

	public String getcCredit() {
		return cCredit;
	}

	public void setcCredit(String cCredit) {
		this.cCredit = cCredit;
	}

	public String getcLevel() {
		return cLevel;
	}

	public void setcLevel(String cLevel) {
		this.cLevel = cLevel;
	}

	

	public String getcAddr() {
		return cAddr;
	}

	public void setcAddr(String cAddr) {
		this.cAddr = cAddr;
	}

	public String getO_Famousspecialty() {
		return O_Famousspecialty;
	}

	public void setO_Famousspecialty(String o_Famousspecialty) {
		O_Famousspecialty = o_Famousspecialty;
	}

	public String getO_PriceFlag() {
		return O_PriceFlag;
	}

	public void setO_PriceFlag(String o_PriceFlag) {
		O_PriceFlag = o_PriceFlag;
	}

	public String getO_bNew() {
		return O_bNew;
	}

	public void setO_bNew(String o_bNew) {
		O_bNew = o_bNew;
	}

	public String getcProductdDate() {
		return cProductdDate;
	}

	public void setcProductdDate(String cProductdDate) {
		this.cProductdDate = cProductdDate;
	}

	public String getcStoreNo() {
		return cStoreNo;
	}

	public void setcStoreNo(String cStoreNo) {
		this.cStoreNo = cStoreNo;
	}

	public String getcStoreName() {
		return cStoreName;
	}

	public void setcStoreName(String cStoreName) {
		this.cStoreName = cStoreName;
	}

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

	public String getcGoodsTypeno() {
		return cGoodsTypeno;
	}

	public void setcGoodsTypeno(String cGoodsTypeno) {
		this.cGoodsTypeno = cGoodsTypeno;
	}

	public String getcGoodsTypename() {
		return cGoodsTypename;
	}

	public void setcGoodsTypename(String cGoodsTypename) {
		this.cGoodsTypename = cGoodsTypename;
	}

	public BigDecimal getfNormalPrice() {
		return fNormalPrice;
	}

	public void setfNormalPrice(BigDecimal fNormalPrice) {
		this.fNormalPrice = fNormalPrice;
	}

	public BigDecimal getfVipPrice() {
		return fVipPrice;
	}

	public void setfVipPrice(BigDecimal fVipPrice) {
		this.fVipPrice = fVipPrice;
	}

	public BigDecimal getfVipPrice_student() {
		return fVipPrice_student;
	}

	public void setfVipPrice_student(BigDecimal fVipPrice_student) {
		this.fVipPrice_student = fVipPrice_student;
	}
}
