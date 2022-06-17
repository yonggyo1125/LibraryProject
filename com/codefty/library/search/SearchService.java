package com.codefty.library.search;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import com.codefty.library.book.*;

/**
 * 도서 조회 기능
 * @author YONGGYO
 */
public class SearchService {
	
	private static SearchService instance = new SearchService();
	
	private SearchService() {}
	
	// 검색결과 저장
	private List<Book> searchResults;  
	
	public List<Book> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<Book> searchResults) {
		this.searchResults = searchResults;
	}

	/**
	 * 도서 목록 조회 
	 * 
	 * @param sopt : 검색조건 
	 * @param skey : 검색 키워드
	 * @param statuses : 대여 상태 
	 * @return SearchService
	 */
	public SearchService search(String sopt, String skey, List<String> statuses) {
		
		List<Book> books = BookDao.getInstance().gets();

		/** 조건 + 키워드 검색 조건 처리 S */
		Predicate<Book> keyWordsSearch = new Predicate<>() {
			@Override
			public boolean test(Book book) {
				/** 검색조건과 키워드가 누락된 경우는 전체 목록 조회 */
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
		/** 조건 + 키워드 검색 조건 처리 E */
		
		/** 도서 대여 상태 검색 조건 S */
		Predicate<Book> statusSearch = new Predicate<>() {
			@Override
			public boolean test(Book book) {
				// 대여 상태 검색 조건이 없는 경우 모두 통과 
				if (statuses == null || statuses.size() == 0)
					return true;
				
				return statuses.contains(book.getStatus().getStatusStr());
			}
		};
		/** 도서 대여 상태 검색 조건 E */
		
		/** 검색 수행 및 처리 결과 저장 S */
		List<Book> searchResults = books.stream()
														.filter(keyWordsSearch)
														.filter(statusSearch)
														.collect(Collectors.toList());
		
		setSearchResults(searchResults);
		/** 검색 수행 및 처리 결과 저장 E */
		
		return this;
	}
	
	public SearchService search(String sopt, String skey) {
		return search(sopt, skey, null);
	}
	
	/**
	 * 검색 결과를 List로 변환
	 * 
	 * @return List<Book>
	 */
	public List<Book> toList() {
		
		return searchResults;
	}
	
	/**
	 * 검색결과를 String 배열로 변환
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
	 * 검색 데이터 생성
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
			case "통합검색" : 
				StringBuffer sb = new StringBuffer();
				sb.append(book.getTitle());
				sb.append(book.getAuthor());
				sb.append(book.getPublisher());
				sContents = sb.toString();
				break;
			case "도서명" :
				sContents = book.getTitle();
				break;
			case "출판사" : 
				sContents = book.getPublisher();
				break;
			case "저자" :
				sContents = book.getAuthor();
				break;
		}
		
		return sContents;
	}
	
	/**
	 * 싱글톤(SingleTon) 방식으로 객체 반환
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
