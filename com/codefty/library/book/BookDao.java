package com.codefty.library.book;

import java.util.*;
import java.io.*;

/**
 * ������ ���, ����, ��ȸ, ������ ������ ���� ó�� 
 * 
 * @author YONGGYO
 */
public class BookDao {
	
	private static BookDao instance = new BookDao();
	
	private List<Book> books;
	private static long serialNum; // �Ϸù�ȣ
	private String dataPath;
	
	private BookDao() {
		/** ������ ���� ��� S */
		dataPath = new File("").getAbsolutePath() + File.separator + "data";
		File file = new File(dataPath);
		if (!file.exists()) {
			file.mkdir();
		}
		
		dataPath += File.separator + "books.obj";
		/** ������ ���� ��� E */
		
		// ���� �Ϸù�ȣ 
		serialNum = 1000000L;
		
		 books = loadBooks();
	}
	
	/**
	 * ���� ���� 
	 * 
	 * @param book
	 */
	public void register(Book book) {
		if (book == null)
			return;
		
		book.setSerialNum(serialNum);
		books.add(book);
		
		// �Ϸ� ��ȣ ����
		serialNum++;
		
		// ���� ��� ����
		saveBooks();
	}
	
	/**
	 * ���� ���� ���� 
	 * 
	 * @param book
	 */
	public void update(Book book) {
		for (int i = 0; i < books.size(); i++) {
			Book _book = books.get(i);
			if (book.getSerialNum() == _book.getSerialNum()) {
				books.set(i, book);
			}
		}
		
		// ���� ��� ����
		saveBooks();
	}
	
	/**
	 * ���� ���� ���� 
	 * 
	 * @param book
	 */
	public void delete(Book book) {
		books.remove(book);
		
		// ���� ��� ����
		saveBooks();
	}
	
	/**
	 * ����� ���� ��� ��������
	 *  
	 * @return 
	 */
	public List<Book> loadBooks() {
		try(FileInputStream fis = new FileInputStream(dataPath);
			ObjectInputStream ois = new ObjectInputStream(fis)) {
			List<Book> books = (List<Book>)ois.readObject();
			/** ���� ��� ���� �Ϸù�ȣ ������Ʈ */
			if (books.size() > 0) {
				Book book = books.get(books.size() - 1); // ������ ��� ��� ��ȸ
				serialNum = book.getSerialNum() + 1;
			}
			
			return books;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return new LinkedList<Book>();
	}
	
	/**
	 * ���� ��� �����ϱ� 
	 * 
	 */
	public void saveBooks() {
		try(FileOutputStream fos = new FileOutputStream(dataPath);
			ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(books);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �̱���(SingleTon) ������� ��ü ��ȯ
	 * 
	 * @return
	 */
	public static BookDao getInstance() {
		if (instance == null ) {
			instance = new BookDao();
		}
		
		return instance;
	}
}
