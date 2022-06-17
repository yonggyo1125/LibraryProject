package com.codefty.library.book;

/**
 * ���� ���, ����, ���� ��� ó��
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
	 * �Ϸù�ȣ�� ���� ��ȸ 
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
	 * ���� ��� ���� üũ 
	 * 
	 * @param Book book
	 * @throws BookNotExistsException
	 */
	private void isBookExists(Book book) {
		isBookExists(book.getSerialNum());
	}
	
	/**
	 * ���� ��� ���� üũ 
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
	 * �̱���(SingleTon) ������� ��ü ��ȯ
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
