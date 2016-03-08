package com.hl_zhaoq.digou.bean;

import java.math.BigDecimal;
import java.util.List;

//gson�����������list
public class StoreItem {

	// [{"cStoreNo":"10001","cStoreName":"���г���","cCredit":"05","cLevel":"3 ",
	// "fDistance":55.6,"logPic":"null","cAddr":"�����к������ϵ�7�ֹ��ʴ�ҵ԰5E",
	// "Pinpai":[{"cStoreNo":"10001","O_cPinpaiNo":"1001","O_cPinpaiName":"����",
	// "cPinpaiGrantNo":"101011010","cPinpaiGrantPic":"/pinpaigrantpic/1001.jpg"}]}]
//"PeisongFee_Over_Free":null,"PeisongFee":null,"cSameMonthSales":0,"cSameYearSales":0,"MM_Valid":false},
	public String cStoreNo;
	public String cStoreName;
	public String cCredit;
	public String cLevel;
	public double fDistance;
	public String logPic;
	public String cAddr;
	public List<StoreItem3T> Pinpai;
	public String OverCut;
	public String FirstSheet;
	public String GiftFree;
	public BigDecimal Over_Money;
	public BigDecimal Cut_Money;
	public BigDecimal FirstSheet_Over;
	public BigDecimal FirstSheet_Cut;
	public BigDecimal GiftFree_Over;
//	public BigDecimal GiftFree_GiftMoney;
	public String GiftFree_GiftGoodsNo;
	public BigDecimal PeisongFee_Over_Free;
//	public BigDecimal BasicPrice;
	public BigDecimal PeisongFee;
	public Integer cSameMonthSales;
	public Integer cSameYearSales;
	public List<Activity> Activity;
	public boolean MM_Valid;
	
	public boolean isMM_Valid() {
		return MM_Valid;
	}

	public void setMM_Valid(boolean mM_Valid) {
		MM_Valid = mM_Valid;
	}

	public List<Activity> getActivity() {
		return Activity;
	}

	public void setActivity(List<Activity> activity) {
		Activity = activity;
	}

	public String getOverCut() {
		return OverCut;
	}

	public void setOverCut(String overCut) {
		OverCut = overCut;
	}

	public String getFirstSheet() {
		return FirstSheet;
	}

	public void setFirstSheet(String firstSheet) {
		FirstSheet = firstSheet;
	}

	public String getGiftFree() {
		return GiftFree;
	}

	public void setGiftFree(String giftFree) {
		GiftFree = giftFree;
	}

	public BigDecimal getOver_Money() {
		return Over_Money;
	}

	public void setOver_Money(BigDecimal over_Money) {
		Over_Money = over_Money;
	}

	public BigDecimal getCut_Money() {
		return Cut_Money;
	}

	public void setCut_Money(BigDecimal cut_Money) {
		Cut_Money = cut_Money;
	}

	public BigDecimal getFirstSheet_Over() {
		return FirstSheet_Over;
	}

	public void setFirstSheet_Over(BigDecimal firstSheet_Over) {
		FirstSheet_Over = firstSheet_Over;
	}

	public BigDecimal getFirstSheet_Cut() {
		return FirstSheet_Cut;
	}

	public void setFirstSheet_Cut(BigDecimal firstSheet_Cut) {
		FirstSheet_Cut = firstSheet_Cut;
	}

	public BigDecimal getGiftFree_Over() {
		return GiftFree_Over;
	}

	public void setGiftFree_Over(BigDecimal giftFree_Over) {
		GiftFree_Over = giftFree_Over;
	}

	public String getGiftFree_GiftGoodsNo() {
		return GiftFree_GiftGoodsNo;
	}

	public void setGiftFree_GiftGoodsNo(String giftFree_GiftGoodsNo) {
		GiftFree_GiftGoodsNo = giftFree_GiftGoodsNo;
	}

	public BigDecimal getPeisongFee_Over_Free() {
		return PeisongFee_Over_Free;
	}

	public void setPeisongFee_Over_Free(BigDecimal peisongFee_Over_Free) {
		PeisongFee_Over_Free = peisongFee_Over_Free;
	}

	public BigDecimal getPeisongFee() {
		return PeisongFee;
	}

	public void setPeisongFee(BigDecimal peisongFee) {
		PeisongFee = peisongFee;
	}

	public Integer getcSameMonthSales() {
		return cSameMonthSales;
	}

	public void setcSameMonthSales(Integer cSameMonthSales) {
		this.cSameMonthSales = cSameMonthSales;
	}

	public Integer getcSameYearSales() {
		return cSameYearSales;
	}

	public void setcSameYearSales(Integer cSameYearSales) {
		this.cSameYearSales = cSameYearSales;
	}

	
	public List<StoreItem3T> getPinpai() {
		return Pinpai;
	}

	public void setPinpai(List<StoreItem3T> pinpai) {
		Pinpai = pinpai;
	}

	//
	public static class StoreItem3T {
		public String cStoreNo;
		public String O_cPinpaiNo;
		public String O_cPinpaiName;
		public String cPinpaiGrantNo;
		public String cPinpaiGrantPic;

		public String getcStoreNo() {
			return cStoreNo;
		}

		public void setcStoreNo(String cStoreNo) {
			this.cStoreNo = cStoreNo;
		}

		public String getO_cPinpaiNo() {
			return O_cPinpaiNo;
		}

		public void setO_cPinpaiNo(String o_cPinpaiNo) {
			O_cPinpaiNo = o_cPinpaiNo;
		}

		public String getO_cPinpaiName() {
			return O_cPinpaiName;
		}

		public void setO_cPinpaiName(String o_cPinpaiName) {
			O_cPinpaiName = o_cPinpaiName;
		}

		public String getcPinpaiGrantNo() {
			return cPinpaiGrantNo;
		}

		public void setcPinpaiGrantNo(String cPinpaiGrantNo) {
			this.cPinpaiGrantNo = cPinpaiGrantNo;
		}

		public String getcPinpaiGrantPic() {
			return cPinpaiGrantPic;
		}

		public void setcPinpaiGrantPic(String cPinpaiGrantPic) {
			this.cPinpaiGrantPic = cPinpaiGrantPic;
		}

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

	public double getfDistance() {
		return fDistance;
	}

	public void setfDistance(double fDistance) {
		this.fDistance = fDistance;
	}

	public String getLogPic() {
		return logPic;
	}

	public void setLogPic(String logPic) {
		this.logPic = logPic;
	}

	public String getcAddr() {
		return cAddr;
	}

	public void setcAddr(String cAddr) {
		this.cAddr = cAddr;
	}
}
