package com.codefty.library.book;

/**
 * ���� ���, ����, ���� ��� ó��
 *  
 * @author YONGGYO
 */
public class BookService implements DeleteBook, RegisterBook,  UpdateBook {

	@Override
	public void update(Book book) {
		BookDao.getInstance().update(book);
	}

	@Override
	public void register(Book book) {
		BookDao.getInstance().register(book);
	}

	@Override
	public void delete(Book book) {
		BookDao.getInstance().delete(book);
	}
}
