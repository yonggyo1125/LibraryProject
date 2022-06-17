package com.codefty.library.search;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import com.codefty.library.book.*;

/**
 * ���� ��ȸ ���
 * @author YONGGYO
 */
public class SearchService {
	
	private static SearchService instance = new SearchService();
	
	private SearchService() {}
	
	// �˻���� ����
	private List<Book> searchResults;  
	
	public List<Book> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<Book> searchResults) {
		this.searchResults = searchResults;
	}

	/**
	 * ���� ��� ��ȸ 
	 * 
	 * @param sopt : �˻����� 
	 * @param skey : �˻� Ű����
	 * @param statuses : �뿩 ���� 
	 * @return SearchService
	 */
	public SearchService search(String sopt, String skey, List<String> statuses) {
		
		List<Book> books = BookDao.getInstance().gets();

		/** ���� + Ű���� �˻� ���� ó�� S */
		Predicate<Book> keyWordsSearch = new Predicate<>() {
			@Override
			public boolean test(Book book) {
				/** �˻����ǰ� Ű���尡 ������ ���� ��ü ��� ��ȸ */
				if (sopt.isBlank() || skey.isBlank()) {
					return true;
				}
				
				String sContent = getSearchContent(book, sopt);
				if (sContent != null) {
					return sContent.indexOf(skey) != -1;
				}
				
				return false;
			}
		};
		/** ���� + Ű���� �˻� ���� ó�� E */
		
		/** ���� �뿩 ���� �˻� ���� S */
		Predicate<Book> statusSearch = new Predicate<>() {
			@Override
			public boolean test(Book book) {
				// �뿩 ���� �˻� ������ ���� ��� ��� ��� 
				if (statuses == null || statuses.size() == 0)
					return true;
				
				return statuses.contains(book.getStatus().getStatusStr());
			}
		};
		/** ���� �뿩 ���� �˻� ���� E */
		
		/** �˻� ���� �� ó�� ��� ���� S */
		List<Book> searchResults = books.stream()
														.filter(keyWordsSearch)
														.filter(statusSearch)
														.collect(Collectors.toList());
		
		setSearchResults(searchResults);
		/** �˻� ���� �� ó�� ��� ���� E */
		
		return this;
	}
	
	public SearchService search(String sopt, String skey) {
		return search(sopt, skey, null);
	}
	
	/**
	 * �˻� ����� List�� ��ȯ
	 * 
	 * @return List<Book>
	 */
	public List<Book> toList() {
		
		return searchResults;
	}
	
	/**
	 * �˻������ String �迭�� ��ȯ
	 * 
	 * @return String[][]
	 */
	public String[][] toArray() {
		String[][] searchData = searchResults.stream()
												.map(b -> {
													return new String[] {
																String.valueOf(b.getSerialNum()),
																b.getTitle(),
																b.getPublisher(),
																b.getAuthor(),
																b.getStatus().getStatusStr()
													};
												}).toArray(String[][]::new);
		
		return searchData;
	}
	
	/**
	 * �˻� ������ ����
	 *  
	 * @param book
	 * @param sopt
	 * @return String
	 */
	private String getSearchContent(Book book, String sopt) {
		if (book == null) 
			return null;
		
		String sContents = null;
		switch (sopt) {
			case "���հ˻�" : 
				StringBuffer sb = new StringBuffer();
				sb.append(book.getTitle());
				sb.append(book.getAuthor());
				sb.append(book.getPublisher());
				sContents = sb.toString();
				break;
			case "������" :
				sContents = book.getTitle();
				break;
			case "���ǻ�" : 
				sContents = book.getPublisher();
				break;
			case "����" :
				sContents = book.getAuthor();
				break;
		}
		
		return sContents;
	}
	
	/**
	 * �̱���(SingleTon) ������� ��ü ��ȯ
	 * 
	 * @return SearchService
	 */
	public static SearchService getInstance() {
		if (instance == null ) {
			instance = new SearchService();
		}
		
		return instance;
	}
}
