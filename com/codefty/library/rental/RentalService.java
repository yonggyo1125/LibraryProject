package com.codefty.library.rental;

import com.codefty.library.book.Book;

/**
 * ���� ����, �ݳ� ���
 * 
 * @author YONGGYO
 */
public class RentalService implements RentBook, ReturnBook {

	@Override
	public boolean returnBook(Book book) {
		
		return false;
	}

	@Override
	public boolean rentBook(Book book) {
		
		return false;
	}

}
