package com.hlcxdg.digou.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.google.gson.Gson;

public class SaleSheet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BigDecimal CouPonMoney = BigDecimal.valueOf(0.0);
	public Boolean SEvaluation; // 是否评价过，true/false
	// public BigDecimal CouPonMoney;
	public String dSaleDate = "";
	public String cSaleSheetno = "";
	public String userNo = "";
	public String cStoreNo = "";
	public String cStoreName = "";
	public BigDecimal OverCut = BigDecimal.valueOf(0.0); // 满减
	public BigDecimal FirstSheet = BigDecimal.valueOf(0.0); // 首单减费用
	public BigDecimal PeisongFee = BigDecimal.valueOf(0.0); // 配送费
	public BigDecimal fMoney = BigDecimal.valueOf(0.0); // 未处理结算价格
	public BigDecimal fLastMoney = BigDecimal.valueOf(0.0); // 结算减后价格
	public String cVipNo = "";
	public int AddressId = 1; //
	public String Stat_Id = "00"; //
	public String Beizhu = "";
	public String Fapiao = "";
	public String FapiaoTitle = "";
	public String FapiaoStat = "";
	public String bSent = "";
	public List<SaleSheetDetail> SaleSheetDetailList;
	public String SaleSheetDetailStr = "";
	public String Stat_Name;

	public BigDecimal getCouPonMoney() {
		return CouPonMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setCouPonMoney(BigDecimal couPonMoney) {
		CouPonMoney = couPonMoney;
	}

	public Boolean getSEvaluation() {
		return SEvaluation;
	}

	public void setSEvaluation(Boolean sEvaluation) {
		SEvaluation = sEvaluation;
	}

	public String getbSent() {
		return bSent;
	}

	public void setbSent(String bSent) {
		this.bSent = bSent;
	}

	public void setSaleSheetDetailStr(String saleSheetDetailStr) {
		SaleSheetDetailStr = saleSheetDetailStr;
	}

	public BigDecimal getOverCut() {
		return OverCut.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setOverCut(BigDecimal overCut) {
		OverCut = overCut;
	}

	public BigDecimal getFirstSheet() {
		return FirstSheet.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setFirstSheet(BigDecimal firstSheet) {
		FirstSheet = firstSheet;
	}

	public BigDecimal getPeisongFee() {
		return PeisongFee.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setPeisongFee(BigDecimal peisongFee) {
		PeisongFee = peisongFee;
	}

	public String getStat_Name() {
		return Stat_Name;
	}

	public void setStat_Name(String stat_Name) {
		Stat_Name = stat_Name;
	}

	public String getSaleSheetDetailStr() {
		return SaleSheetDetailStr;
	}

	public void setSaleSheetDetailStr() {

		for (int i = 0; i < SaleSheetDetailList.size(); i++) {
			SaleSheetDetailList.get(i).cGoodsName = null;
			SaleSheetDetailList.get(i).MinIMG0 = null;
			SaleSheetDetailList.get(i).MinIMG1 = null;
		}
		Gson gson = new Gson();
		String t = gson.toJson(SaleSheetDetailList);
		SaleSheetDetailStr = t;
		SaleSheetDetailList.clear();
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

	public BigDecimal getfMoney() {
		return fMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setfMoney(BigDecimal fMoney) {
		this.fMoney = fMoney;
	}

	public BigDecimal getfLastMoney() {
		return fLastMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
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

	public int getAddressID() {
		return AddressId;
	}

	public void setAddressID(int addressID) {
		AddressId = addressID;
	}

	public List<SaleSheetDetail> getSaleSheetDetailList() {
		return SaleSheetDetailList;
	}

	public void setSaleSheetDetailList(List<SaleSheetDetail> saleSheetDetailList) {
		SaleSheetDetailList = saleSheetDetailList;
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

	public String getDSaleDate() {
		return dSaleDate;
	}

	public void setDSaleDate(String dSaleDate) {
		this.dSaleDate = dSaleDate;
	}

	public String getCSaleSheetno() {
		return cSaleSheetno;
	}

	public void setCSaleSheetno(String cSaleSheetno) {
		this.cSaleSheetno = cSaleSheetno;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public BigDecimal getFMoney() {
		return fMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setFMoney(BigDecimal fMoney) {
		this.fMoney = fMoney;
	}

	public BigDecimal getFLastMoney() {
		return fLastMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setFLastMoney(BigDecimal fLastMoney) {
		this.fLastMoney = fLastMoney;
	}

	public String getCVipNo() {
		return cVipNo;
	}

	public void setCVipNo(String cVipNo) {
		this.cVipNo = cVipNo;
	}

	public int getAddressId() {
		return AddressId;
	}

	public void setAddressId(int addressId) {
		this.AddressId = addressId;
	}

	public String getStat_Id() {
		return Stat_Id;
	}

	public void setStat_Id(String stat_Id) {
		this.Stat_Id = stat_Id;
	}

	public String getBeizhu() {
		return Beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.Beizhu = beizhu;
	}

	public String getFapiao() {
		return Fapiao;
	}

	public void setFapiao(String fapiao) {
		this.Fapiao = fapiao;
	}

	public String getFapiaoTitle() {
		return FapiaoTitle;
	}

	public void setFapiaoTitle(String fapiaoTitle) {
		this.FapiaoTitle = fapiaoTitle;
	}

	public String getFapiaoStat() {
		return FapiaoStat;
	}

	public void setFapiaoStat(String fapiaoStat) {
		this.FapiaoStat = fapiaoStat;
	}

	public String getBSent() {
		return bSent;
	}

	public void setBSent(String bSent) {
		this.bSent = bSent;
	}

}
