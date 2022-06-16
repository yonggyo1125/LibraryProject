package com.codefty.library.gui;

import java.util.*;
import java.util.stream.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.codefty.library.book.Book;
import com.codefty.library.book.BookService;
import com.codefty.library.common.CommonException;

public class BookFormUI extends JPanel implements ActionListener {
	
	private long serialNum;
	
	JPanel wrapBox, topBox, contentBox, bottomBox;
	JButton submitBtn, modifyBtn;
	JTextField bookTitle, publisher, author;
	JComboBox<Integer> rentalDays;
	
	Integer[] rentalDuration = { 1, 2, 3, 4, 5, 6, 7, }; // 대여 기간
	
	/**
	 * 도서 등록 UI
	 * 
	 */
	public BookFormUI() {
		super(new BorderLayout());
		
		wrapBox = new JPanel(new BorderLayout());
		
		/** 상단 S */
		topBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topBox.add(new JLabel("도서 등록"));
		topBox.setBackground(Color.WHITE);
		wrapBox.add(topBox, BorderLayout.NORTH);
		/** 상단 E */
		
		/** 내용 영역 S */
		contentBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		contentBox.setBackground(Color.WHITE);
		contentBox.add(new JLabel("도서명 : "));
		contentBox.add(bookTitle = new JTextField(45));
	
		contentBox.add(new JLabel("출판사 : "));
		contentBox.add(publisher = new JTextField(45));
		
		contentBox.add(new JLabel("저자     : "));
		contentBox.add(author = new JTextField(45));
		
		contentBox.add(new JLabel("대여 가능일 수 : "));
		rentalDays = new JComboBox<>(rentalDuration);
		rentalDays.setBackground(Color.WHITE);
		contentBox.add(rentalDays);
		contentBox.add(new JLabel("일"));
		
		wrapBox.add(contentBox, BorderLayout.CENTER);
		/** 내용 영역 E */
		
		/** 하단 S */
		submitBtn = new JButton("등록하기");
		submitBtn.addActionListener(this);
		wrapBox.add(submitBtn, BorderLayout.SOUTH);
		/** 하단 E */
		
		add(wrapBox, BorderLayout.CENTER);
	}
	
	/**
	 * 도서 수정 UI
	 * 
	 * @param serialNum : 도서 일련번호
	 */
	public BookFormUI(long serialNum) {
		this();
		
		this.serialNum = serialNum;
		
		/** 상단 S */
		topBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topBox.add(new JLabel("도서 일련 번호 : " + serialNum));
		topBox.setBackground(Color.WHITE);
		wrapBox.add(topBox, BorderLayout.NORTH);
		/** 상단 E */
		
		
		/** 하단 S */
		modifyBtn = new JButton("수정하기");
		modifyBtn.addActionListener(this);
		wrapBox.add(modifyBtn, BorderLayout.SOUTH);
		/** 하단 E */
		
		updateUI();
		
		// 도서 수정 정보 입력 
		try {
			updateBookInfo(serialNum);
		} catch (CommonException e) {
			e.printStackTrace();
			// 도서 정보가 존재하지 않으면 다시 목록으로 이동 
			moveToBookList();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			// 입력 데이터 유효성 검사
			validateForm();
			
			// 진행여부 
			StringBuffer sb = new StringBuffer("정말 "); 
			sb.append((e.getSource() == modifyBtn)?"수정":"등록");
			sb.append(" 하시겠습니까?");
			String msg = sb.toString();
			 
			int result = JOptionPane.showConfirmDialog(this, msg, "확인", JOptionPane.YES_NO_OPTION);
			if (result == 1) { // 취소한 경우는 중단
				return;
			}
			
			BookService bookSvc = new BookService();
			Book book = getBookInfo();
						
			if (e.getSource() == modifyBtn) { // 수정하기 
				bookSvc.update(book);
			} else { // 등록하기
				bookSvc.register(book);
			}
			
			// 등록 또는 수정이 완료 되었다면 도서 목록으로 이동
			moveToBookList();
			
		} catch (CommonException _e) {
			_e.printStackTrace();
		}
	}
	
	/**
	 * 사용자가 입력한 도서 정보 추출
	 *  
	 * @return {Book}
	 */
	private Book getBookInfo() {
		String title = bookTitle.getText().trim();
		String publisher = this.publisher.getText().trim();
		String author = this.author.getText().trim();
		Integer rentalDays = (Integer)this.rentalDays.getSelectedItem();
		Book book = new Book(serialNum, title, publisher, author, rentalDays.intValue());
		
		return book;
	}
	
	/**
	 * 도서 정보 수정시 필드에 도서 정보 입력
	 * 
	 * @param serialNum
	 * @throws {CommonException} 도서 정보가 없는 경우
	 */
	private void updateBookInfo(long serialNum) {
		// 실제 저장된 도서 정보를 가져오는 부분을 담당하시는 분이 구현해 주세요.
		/** 
	    Book book =  ...
		if (book == null) {
			throw CommonException(this, "등록되지 않은 도서입니다.");
		}
		
		bookTitle.setText(book.getTitle());
		publisher.setText(book.getPublisher());
		author.setText(book.getAuthor());
		rentalDays.setSelectedItem(book.getRentalDays());
		*/
	}
	
	private void validateForm() {
		Book book = getBookInfo();
		ArrayList<String> fields = new ArrayList<>(); 
		if (book.getTitle().isEmpty()) {
			fields.add("도서명");
		}
		
		if (book.getPublisher().isEmpty()) {
			fields.add("출판사");
		}
		
		if (book.getAuthor().isEmpty()) {
			fields.add("저자");
		}
		
		// 누락된 항목이 있다면
		if (fields.size() > 0) {
			String missing = fields.stream().collect(Collectors.joining(","));
			String message = "누락항목을 모두 입력하세요(" + missing + ")";
			throw new CommonException(this, message);
		}
	}
	
	/**
	 * 도서 목록으로 이동 
	 * 
	 */
	private void moveToBookList() {
		remove(wrapBox);
		wrapBox = new BookListUI();
		add(wrapBox, BorderLayout.CENTER);
		updateUI();
	}
}