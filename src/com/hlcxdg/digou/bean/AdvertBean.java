package com.hlcxdg.digou.bean;

import java.util.Date;

public class AdvertBean {
	// [adNo] [varchar](32) NOT NULL, //编码
	// [cStoreNo] [varchar](32) NOT NULL,
	// [adImageName] [varchar](64) NULL,
	// [adImagePath] [varchar](128) NULL,
	// [adcGoodsNo] [varchar](32) NULL,
	// [adcSupNo] [varchar](32) NULL,
	// [adWeizhi] [char](2) NULL,
	// [adBgnDate] [datetime] NULL,
	// [adEndDate] [datetime] NULL,
	// [adPaixu] [int] NULL,
	// [adSkipUrl] [varchar](128) NULL,
	public String adStatus;
	public String cStoreName="";
	public String WServerID;
	public String cAddr;

	private String adNo="";
	private String cStoreNo="";
	private String adImageName="";
	private String adImagePath="";
	private String adcGoodsNo="";
	private String adcSupNo;
	private String adWeizhi="";// 横向01、02
	private String adBgnDate;
	private String adEndDate;
	private int adPaixu;// 顺序01、02
	private String adSkipUrl;

	public String getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(String adStatus) {
		this.adStatus = adStatus;
	}

	public String getcAddr() {
		return cAddr;
	}

	public void setcAddr(String cAddr) {
		this.cAddr = cAddr;
	}

	public String getcStoreName() {
		return cStoreName;
	}

	public void setcStoreName(String cStoreName) {
		this.cStoreName = cStoreName;
	}

	public String getWServerID() {
		return WServerID;
	}

	public void setWServerID(String wServerID) {
		WServerID = wServerID;
	}

	public String getAdNo() {
		return adNo;
	}

	public void setAdNo(String adNo) {
		this.adNo = adNo;
	}

	public String getcStoreNo() {
		return cStoreNo;
	}

	public void setcStoreNo(String cStoreNo) {
		this.cStoreNo = cStoreNo;
	}

	public String getAdImageName() {
		return adImageName;
	}

	public void setAdImageName(String adImageName) {
		this.adImageName = adImageName;
	}

	public String getAdImagePath() {
		return adImagePath;
	}

	public void setAdImagePath(String adImagePath) {
		this.adImagePath = adImagePath;
	}

	public String getAdcGoodsNo() {
		return adcGoodsNo;
	}

	public void setAdcGoodsNo(String adcGoodsNo) {
		this.adcGoodsNo = adcGoodsNo;
	}

	public String getAdcSupNo() {
		return adcSupNo;
	}

	public void setAdcSupNo(String adcSupNo) {
		this.adcSupNo = adcSupNo;
	}

	public String getAdWeizhi() {
		return adWeizhi;
	}

	public void setAdWeizhi(String adWeizhi) {
		this.adWeizhi = adWeizhi;
	}

	public String getAdBgnDate() {
		return adBgnDate;
	}

	public void setAdBgnDate(String adBgnDate) {
		this.adBgnDate = adBgnDate;
	}

	public String getAdEndDate() {
		return adEndDate;
	}

	public void setAdEndDate(String adEndDate) {
		this.adEndDate = adEndDate;
	}

	public int getAdPaixu() {
		return adPaixu;
	}

	public void setAdPaixu(int adPaixu) {
		this.adPaixu = adPaixu;
	}

	public String getAdSkipUrl() {
		return adSkipUrl;
	}

	public void setAdSkipUrl(String adSkipUrl) {
		this.adSkipUrl = adSkipUrl;
	}
}
