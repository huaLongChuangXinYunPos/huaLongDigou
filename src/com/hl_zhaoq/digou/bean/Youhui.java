package com.hl_zhaoq.digou.bean;

import java.math.BigDecimal;

//*AIS|RS|MM01|01|[{"cStoreNo":"10001","OverCut":"man200jian10","FirstSheet":"100",
//"GiftFree":"zengsong","Over_Money":200.0000,"Cut_Money":10.0000,"FirstSheet_Over":100.0000,
//"FirstSheet_Cut":10.0000,"GiftFree_GiftGoodsNo":"111","GiftFree_GiftMoney":200.0000,
//"PeisongFee_Over_Free":300.0000,"BasicPrice":10.0000,"PeisongFee":5.0000}]|AIE#

public class Youhui {
	public String cStoreNo;
	public String OverCut;
	public String FirstSheet;
	public String GiftFree;
	public BigDecimal Over_Money;
	public BigDecimal Cut_Money;
	public BigDecimal FirstSheet_Over;
	public BigDecimal FirstSheet_Cut;
	public String GiftFree_GiftGoodsNo;
	public BigDecimal GiftFree_GiftMoney;
	public BigDecimal PeisongFee_Over_Free;
	public BigDecimal BasicPrice;
	public BigDecimal PeisongFee;

	public String getcStoreNo() {
		return cStoreNo;
	}

	public void setcStoreNo(String cStoreNo) {
		this.cStoreNo = cStoreNo;
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

	public String getGiftFree_GiftGoodsNo() {
		return GiftFree_GiftGoodsNo;
	}

	public void setGiftFree_GiftGoodsNo(String giftFree_GiftGoodsNo) {
		GiftFree_GiftGoodsNo = giftFree_GiftGoodsNo;
	}

	public BigDecimal getGiftFree_GiftMoney() {
		return GiftFree_GiftMoney;
	}

	public void setGiftFree_GiftMoney(BigDecimal giftFree_GiftMoney) {
		GiftFree_GiftMoney = giftFree_GiftMoney;
	}

	public BigDecimal getPeisongFee_Over_Free() {
		return PeisongFee_Over_Free;
	}

	public void setPeisongFee_Over_Free(BigDecimal peisongFee_Over_Free) {
		PeisongFee_Over_Free = peisongFee_Over_Free;
	}

	public BigDecimal getBasicPrice() {
		return BasicPrice;
	}

	public void setBasicPrice(BigDecimal basicPrice) {
		BasicPrice = basicPrice;
	}

	public BigDecimal getPeisongFee() {
		return PeisongFee;
	}

	public void setPeisongFee(BigDecimal peisongFee) {
		PeisongFee = peisongFee;
	}

}
