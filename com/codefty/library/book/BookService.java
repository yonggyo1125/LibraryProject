package com.codefty.library.book;

/**
 * 도서 등록, 수정, 삭제 기능 처리
 *  
 * @author YONGGYO
 */
public class BookService implements DeleteBook, RegisterBook,  UpdateBook {
	
	private static BookService instance = new BookService();
	
	private BookDao bookDao;
	
	private BookService() {
		bookDao = BookDao.getInstance();
	}
	
	@Override
	public void update(Book book) {
		
		isBookExists(book);
		
		bookDao.update(book);
	}

	@Override
	public void register(Book book) {		
		bookDao.register(book);
	}

	@Override
	public void delete(Book book) {
		isBookExists(book);
		
		bookDao.delete(book);
	}
	
	/**
	 * 일련번호로 도서 조회 
	 * 
	 * @param serialNum
	 * @return Book
	 * @throws BookNotExistsException
	 */
	public Book get(long serialNum) {
		Book book = bookDao.get(serialNum);
		if (book == null) {
			throw new BookNotExistsException(null);
		}
		
		return book;
	}
	
	/**
	 * 도서 등록 여부 체크 
	 * 
	 * @param Book book
	 * @throws BookNotExistsException
	 */
	private void isBookExists(Book book) {
		isBookExists(book.getSerialNum());
	}
	
	/**
	 * 도서 등록 여부 체크 
	 * 
	 * @param serialNum
	 * @throws BookNotExistsException
	 */
	private void isBookExists(long serialNum) {
		if (!bookDao.isBookExists(serialNum)) {
			throw new BookNotExistsException(null);
		}
	}
	
	/**
	 * 싱글톤(SingleTon) 방식으로 객체 반환
	 * 
	 * @return BookService
	 */
	public static BookService getInstance() {
		if (instance == null ) {
			instance = new BookService();
		}
		
		return instance;
	}
}
