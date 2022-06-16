package com.codefty.library.common;

/**
 * �뿩 ����
 * 
 * @author YONGGYO
 */
public enum RentalStatus {
	READY("���"), // �����  
	RENTAL("�뿩"), // �뿩�� 
	RETURN("�ݳ�"), // �ݳ� 
	CHECKING("�˼�"); // �˼� ��
	
	protected final String statusStr;
	
	RentalStatus(String statusStr) {
		this.statusStr = statusStr;
	}
	
	public String getStatusStr() {
		return statusStr;
	}
}
