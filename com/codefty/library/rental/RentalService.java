package com.codefty.library.rental;

import java.time.*;

import com.codefty.library.book.Book;
import com.codefty.library.book.BookService;
import com.codefty.library.common.RentalStatus;
import static com.codefty.library.common.RentalStatus.*;

/**
 * 도서 대기, 대여, 반납, 검수 기능
 * 
 * @author YONGGYO
 */
public class RentalService implements ReadyBook, RentBook, ReturnBook, CheckingBook  {
	
	private static RentalService instance = new RentalService();
	
	private BookService bookService;
	
	private RentalService() {
		bookService = BookService.getInstance();
	}
	/**
	 * 도서 반납
	 * 
	 */
	@Override
	public void returnBook(Book book) {		
		changeStatus(book, RETURN);
	}
	
	public void returnBook(long serialNum) {
		Book book = bookService.get(serialNum);
		returnBook(book);
	}
	
	/**
	 * 도서 대여
	 * 
	 */
	@Override
	public void rentBook(Book book) {
		changeStatus(book, RENTAL);
	}
	
	public void rentBook(long serialNum) {
		Book book = bookService.get(serialNum);
		rentBook(book);
	}
	
	/**
	 * 도서 검수 
	 * 
	 */
	@Override
	public void checkingBook(Book book) {
		changeStatus(book, CHECKING);
	}
	
	public void checkingBook(long serialNum) {
		Book book = bookService.get(serialNum);
		checkingBook(book);
	}
	
	/**
	 * 도서 대기 
	 * 
	 */
	@Override
	public void readyBook(Book book) {
		changeStatus(book, READY);
	}
	
	public void readyBook(long serialNum) {
		Book book = bookService.get(serialNum);
		readyBook(book);
	}
	
	/**
	 * 도서 대여 상태 변경 
	 * 
	 * @param book
	 * @param status
	 * @throws BookNotExistsException
	 */
	private void changeStatus(Book book, RentalStatus status) {
		// 도서가 등록되어 있는지 체크 
		bookService.isBookExists(book);
		
		book.setStatus(status);
		
		// 대여 상태인 경우 대여 기간 계산 
		if (status == RENTAL) {
			LocalDate startDate = LocalDate.now();
			LocalDate endDate = startDate.plusDays(book.getRentalDays());
			book.setRentalStartDate(startDate);
			book.setRentalEndDate(endDate);
		}
		
		bookService.update(book);
	}
	
	/**
	 * 싱글톤(SingleTon) 방식으로 객체 반환
	 * 
	 * @return RentalService
	 */
	public static RentalService getInstance() {
		if (instance == null) {
			instance = new RentalService();
		}
		
		return instance;
	}
}
