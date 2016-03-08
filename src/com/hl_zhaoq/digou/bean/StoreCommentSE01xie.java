package com.hl_zhaoq.digou.bean;

import java.util.Date;

public class StoreCommentSE01xie {
	// ���̱���
	// ����ݱ���
	// �û�ID
	// ����5��4��3��2��1��0
	// ��������
	// [datetime ����ʱ��
	// (100) �ظ�����
	// �ظ�ʱ��
	// �ظ��޸ı�־���µ�����Ĭ��Ϊ��0��
	private String cStoreNo="";//"10002";
	private String cPaySheetNo="";//"234211187238";
	private String cUserNo="";//"2015090898989";
	private float fEvaluation=2f;//"5";
	private String cEvaluationProjDesc="";//"�ܺã��ܺ�..";
	private String cEvaluationTime="";//"2015-09-24 17:19:18";
	private String cEvaluationResponce="";//"лл�ݹ�..";
	private String cResponceTime="";//"2015-09-24 17:19:18";
	private String ReEvaluationFlag="0";
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
	
	public String getcEvaluationProjDesc() {
		return cEvaluationProjDesc;
	}
	public void setcEvaluationProjDesc(String cEvaluationProjDesc) {
		this.cEvaluationProjDesc = cEvaluationProjDesc;
	}
	public String getcEvaluationTime() {
		return cEvaluationTime;
	}
	public void setcEvaluationTime(String cEvaluationTime) {
		this.cEvaluationTime = cEvaluationTime;
	}
	public String getcEvaluationResponce() {
		return cEvaluationResponce;
	}
	public void setcEvaluationResponce(String cEvaluationResponce) {
		this.cEvaluationResponce = cEvaluationResponce;
	}
	public String getcResponceTime() {
		return cResponceTime;
	}
	public void setcResponceTime(String cResponceTime) {
		this.cResponceTime = cResponceTime;
	}
	public String getReEvaluationFlag() {
		return ReEvaluationFlag;
	}
	public void setReEvaluationFlag(String reEvaluationFlag) {
		ReEvaluationFlag = reEvaluationFlag;
	}
	public float getfEvaluation() {
		return fEvaluation;
	}
	public void setfEvaluation(float fEvaluation) {
		this.fEvaluation = fEvaluation;
	}
}
