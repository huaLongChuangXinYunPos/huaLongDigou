package com.hlcxdg.digou.bean;

import java.util.Date;

public class StoreCommentSE02cha {
	// ���̱���
	// ����ݱ���
	// �û�ID
	// ����5��4��3��2��1��0
	// ��������
	// [datetime ����ʱ��
	// (100) �ظ�����
	// �ظ�ʱ��
	// �ظ��޸ı�־���µ�����Ĭ��Ϊ��0��

	public Integer IndexOrder = 0;

	public Integer Count = 9;

	private String cStoreNo = "";// "10002";
	private String cPaySheetNo = "";// "234211187238";
	private String cUserNo = "";// "2015090898989";

	public Integer getIndexOrder() {
		return IndexOrder;
	}

	public void setIndexOrder(Integer indexOrder) {
		IndexOrder = indexOrder;
	}

	public Integer getCount() {
		return Count;
	}

	public void setCount(Integer count) {
		Count = count;
	}

	public String getcStoreNo() {
		return cStoreNo;
	}

	public void setcStoreNo(String cStoreNo) {
		this.cStoreNo = cStoreNo;
	}

	public String getcPaySheetNo() {
		return cPaySheetNo;
	}

	public void setcPaySheetNo(String cPaySheetNo) {
		this.cPaySheetNo = cPaySheetNo;
	}

	public String getcUserNo() {
		return cUserNo;
	}

	public void setcUserNo(String cUserNo) {
		this.cUserNo = cUserNo;
	}
}
