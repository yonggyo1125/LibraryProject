package com.codefty.library.book;

/**
 * ���� ���, ����, ���� ��� ó��
 *  
 * @author YONGGYO
 */
public class BookService implements DeleteBook, RegisterBook,  UpdateBook {

	@Override
	public boolean update(Book book) {

		return false;
	}

	@Override
	public boolean register(Book book) {

		return false;
	}

	@Override
	public boolean delete(Book book) {

		return false;
	}
}
