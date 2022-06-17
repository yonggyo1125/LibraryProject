package com.codefty.library.book;

import java.io.Serializable;
import java.time.*;

import com.codefty.library.common.RentalStatus;


/**
 * Book DTO(Data Transfer Object)
 * 
 * @author YONGGYO
 */
public class Book implements Serializable {
	
	private static final long serialVersionUID = -3582370035375635944L;
	
	private long serialNum; // 도서 일련번호
	private String title; // 도서명
	private String publisher; // 출판사
	private String author; // 저자
	private int rentalDays; // 도서의 대출 가능 일수 
	private RentalStatus status; // 대여 상태 
	private LocalDate rentalStartDate; // 대여 시작일 
	private LocalDate rentalEndDate; // 대여 종료일
	
	public Book(long serialNum, String title, String publisher, String author, int rentalDays) {
		this.serialNum = serialNum;
		this.title = title;
		this.publisher = publisher;
		this.author = author;
		this.rentalDays = rentalDays;
		this.status = RentalStatus.READY; // 대기중(기본값) 
	}
	
	public Book(long serialNum, String title, String publisher, String author, int rentalDays, RentalStatus status) {
		this.serialNum = serialNum;
		this.title = title;
		this.publisher = publisher;
		this.author = author;
		this.rentalDays = rentalDays;
		this.status = status;
	}
	
	public long getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(long serialNum) {
		this.serialNum = serialNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getRentalDays() {
		return rentalDays;
	}

	public void setRentalDays(int rentalDays) {
		this.rentalDays = rentalDays;
	}

	public RentalStatus getStatus() {
		return status;
	}

	public void setStatus(RentalStatus status) {
		this.status = status;
	}

	public LocalDate getRentalStartDate() {
		return rentalStartDate;
	}

	public void setRentalStartDate(LocalDate rentalStartDate) {
		this.rentalStartDate = rentalStartDate;
	}

	public LocalDate getRentalEndDate() {
		return rentalEndDate;
	}

	public void setRentalEndDate(LocalDate rentalEndDate) {
		this.rentalEndDate = rentalEndDate;
	}

	@Override
	public String toString() {
		return "Book [serialNum=" + serialNum + ", title=" + title + ", publisher=" + publisher + ", author=" + author
				+ ", rentalDays=" + rentalDays + ", status=" + status + "]";
	}
}
