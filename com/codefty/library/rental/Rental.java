package com.codefty.library.rental;

import java.time.LocalDate;

import com.codefty.library.common.RentalStatus;

/**
 * Rental DTO(Data Transfer Object)
 * 
 * @author YONGGYO
 */
public class Rental {
	
	long bookSerialNum; // ���� �Ϸù�ȣ 
	RentalStatus status; // �뿩 ���� 
	LocalDate startDate; // �뿩 ������ 
	LocalDate endDate; // �ݳ���
	
	public Rental(long bookSerialNum, RentalStatus status, LocalDate startDate, LocalDate endDate) {
		this.bookSerialNum = bookSerialNum;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public long getBookSerialNum() {
		return bookSerialNum;
	}

	public void setBookSerialNum(long bookSerialNum) {
		this.bookSerialNum = bookSerialNum;
	}

	public RentalStatus getStatus() {
		return status;
	}

	public void setStatus(RentalStatus status) {
		this.status = status;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Rental [bookSerialNum=" + bookSerialNum + ", status=" + status + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
}
