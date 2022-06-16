package com.codefty.library.common;

/**
 * 대여 상태
 * 
 * @author YONGGYO
 */
public enum RentalStatus {
	READY("대기"), // 대기중  
	RENTAL("대여"), // 대여중 
	RETURN("반납"), // 반납 
	CHECKING("검수"); // 검수 중
	
	protected final String statusStr;
	
	RentalStatus(String statusStr) {
		this.statusStr = statusStr;
	}
	
	public String getStatusStr() {
		return statusStr;
	}
}
