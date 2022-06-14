package com.codefty.library.book;

/**
 * 도서 등록, 수정, 삭제 기능 처리
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
