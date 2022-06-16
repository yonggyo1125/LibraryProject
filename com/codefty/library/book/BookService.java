package com.codefty.library.book;

/**
 * 도서 등록, 수정, 삭제 기능 처리
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
