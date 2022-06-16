package com.codefty.library.book;

import java.util.*;
import java.io.*;

/**
 * 도서의 등록, 수정, 조회, 삭제등 데이터 접근 처리 
 * 
 * @author YONGGYO
 */
public class BookDao {
	
	private static BookDao instance = new BookDao();
	
	private List<Book> books;
	private static long serialNum; // 일련번호
	private String dataPath;
	
	private BookDao() {
		/** 데이터 저장 경로 S */
		dataPath = new File("").getAbsolutePath() + File.separator + "data";
		File file = new File(dataPath);
		if (!file.exists()) {
			file.mkdir();
		}
		
		dataPath += File.separator + "books.obj";
		/** 데이터 저장 경로 E */
		
		// 도서 일련번호 
		serialNum = 1000000L;
		
		 books = loadBooks();
	}
	
	/**
	 * 도서 저장 
	 * 
	 * @param book
	 */
	public void register(Book book) {
		if (book == null)
			return;
		
		book.setSerialNum(serialNum);
		books.add(book);
		
		// 일련 번호 증가
		serialNum++;
		
		// 도서 목록 저장
		saveBooks();
	}
	
	/**
	 * 도서 정보 수정 
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
		
		// 도서 목록 저장
		saveBooks();
	}
	
	/**
	 * 도서 정보 삭제 
	 * 
	 * @param book
	 */
	public void delete(Book book) {
		books.remove(book);
		
		// 도서 목록 저장
		saveBooks();
	}
	
	/**
	 * 저장된 도서 목록 가져오기
	 *  
	 * @return 
	 */
	public List<Book> loadBooks() {
		try(FileInputStream fis = new FileInputStream(dataPath);
			ObjectInputStream ois = new ObjectInputStream(fis)) {
			List<Book> books = (List<Book>)ois.readObject();
			/** 다음 등록 도서 일련번호 업데이트 */
			if (books.size() > 0) {
				Book book = books.get(books.size() - 1); // 마지막 등록 요소 조회
				serialNum = book.getSerialNum() + 1;
			}
			
			return books;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return new LinkedList<Book>();
	}
	
	/**
	 * 도서 목록 저장하기 
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
	 * 싱글톤(SingleTon) 방식으로 객체 반환
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
