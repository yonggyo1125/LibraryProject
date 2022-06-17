package com.codefty.library.rental;

import java.time.*;

import com.codefty.library.book.Book;
import com.codefty.library.book.BookService;
import com.codefty.library.common.RentalStatus;
import static com.codefty.library.common.RentalStatus.*;

/**
 * ���� ���, �뿩, �ݳ�, �˼� ���
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
	 * ���� �ݳ�
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
	 * ���� �뿩
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
	 * ���� �˼� 
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
	 * ���� ��� 
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
	 * ���� �뿩 ���� ���� 
	 * 
	 * @param book
	 * @param status
	 * @throws BookNotExistsException
	 */
	private void changeStatus(Book book, RentalStatus status) {
		// ������ ��ϵǾ� �ִ��� üũ 
		bookService.isBookExists(book);
		
		book.setStatus(status);
		
		// �뿩 ������ ��� �뿩 �Ⱓ ��� 
		if (status == RENTAL) {
			LocalDate startDate = LocalDate.now();
			LocalDate endDate = startDate.plusDays(book.getRentalDays());
			book.setRentalStartDate(startDate);
			book.setRentalEndDate(endDate);
		}
		
		bookService.update(book);
	}
	
	/**
	 * �̱���(SingleTon) ������� ��ü ��ȯ
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
