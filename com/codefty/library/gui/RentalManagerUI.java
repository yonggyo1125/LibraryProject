package com.codefty.library.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.codefty.library.book.*;
import com.codefty.library.common.*;

/**
 * 도서 대여, 반납 UI
 * 
 * @author YONGGYO
 */
public class RentalManagerUI extends JPanel implements ActionListener {
	
	JPanel topBox, contentBox, resultBox;
	JTextField serialNumBox;
	JLabel serialNumTxt, statusTxt, bookTitle, publisher, author, rentalDays;
	JButton searchBtn, actionBtn;

	
	public RentalManagerUI() {
		super(new BorderLayout());
		
		/** 상단 영역 S */
		topBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topBox.setBorder(new TitledBorder("도서 조회"));
		topBox.setBackground(Color.WHITE);
		
		serialNumBox = new JTextField(15);
		searchBtn = new JButton("조회하기");
		searchBtn.addActionListener(this);
		topBox.add(serialNumBox);
		topBox.add(searchBtn);
		add(topBox, BorderLayout.NORTH);
		/** 상단 영역 E */
		
		/** 내용 영역 S */
		contentBox = new JPanel(new BorderLayout());
		contentBox.setBackground(Color.WHITE);
		add(contentBox, BorderLayout.CENTER);
		/** 내용 영역 E */
		
		/** 버튼 추가 */
		actionBtn = new JButton();
		add(actionBtn, BorderLayout.SOUTH);
		actionBtn.setVisible(false);
		actionBtn.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == searchBtn) {
				// 조회 결과 업데이트
				updateSearchResult();
			} else if (e.getSource() == actionBtn) {
				
				JButton btn = (JButton)e.getSource();
				String status = btn.getText();
				
				int result = JOptionPane.showConfirmDialog(this, "정말 진행하시겠습까? - " + status , "확인", JOptionPane.YES_NO_OPTION);
				if (result == 1) {
					return;
				}
				
				/** 도서 대여, 반납 담당자가 처리하는 부분 ... */
				switch(status) {
					case "대기하기":
						break;
					case "대여하기":
						break;
					case "반납하기":
						break;
					case "검수하기":
						break;
				}
				
				// 처리 완료 후 도서 정보 갱신
				updateSearchResult();
			}
		} catch (CommonException _e) {
			_e.printStackTrace();
		}
	}
	
	/**
	 * 조회결과 업데이트
	 */
	private void updateSearchResult() {		
		String serialNumStr = serialNumBox.getText();
		if (serialNumStr.isBlank()) {
			throw new CommonException(this, "조회할 일련번호를 입력하세요.");
		}
		
		long serialNum;
		try {
			serialNum = Long.parseLong(serialNumStr.trim());
		} catch (NumberFormatException e) {
			throw new CommonException(this, "일련번호는 숫자만 입력하세요.");
		}
	
		// 일련번호로 도서 조회
		Book book = BookService.getInstance().get(serialNum);
		
		if (resultBox != null) {
			contentBox.remove(resultBox);
		}
		
		int width = getWidth(); // 기준 너비
		
		resultBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		resultBox.setBackground(Color.WHITE);
		String[] label = { "일련번호", "대여상태", "대여기간", "도서명", "출판사", "저자" };
	
		JLabel[] lbs = new JLabel[6];
		for(int i = 0; i < lbs.length; i++) {
			lbs[i] = new JLabel(label[i]);
			lbs[i].setPreferredSize(new Dimension(80, 20));
			resultBox.add(lbs[i]);
			Dimension d = new Dimension(width - 90, 20);
			
			String rentalPeriods; // 렌탈기간
			
			switch (i) {
				case 0 :
					serialNumTxt = new JLabel("" + serialNum);
					serialNumTxt.setPreferredSize(d);
					resultBox.add(serialNumTxt);
					break;
				case 1 : 
					statusTxt = new JLabel(book.getStatus().getStatusStr());
					statusTxt.setPreferredSize(d);
					resultBox.add(statusTxt);
					break;
				case 2 : 
					// 대여, 반납 기능 구현 담당자가 처리해 주세요.
					if (book.getStatus() == RentalStatus.READY) {
						rentalPeriods = "대여일로 부터 " + book.getRentalDays() + "일간";
					} else {
						rentalPeriods = "렌탈 기간...";
					}
					rentalDays = new JLabel(rentalPeriods);
					rentalDays.setPreferredSize(d);
					resultBox.add(rentalDays);
					break;
				case 3 : 
					bookTitle = new JLabel(book.getTitle());
					bookTitle.setPreferredSize(d);
					resultBox.add(bookTitle);
					break;
				case 4 : 
					publisher = new JLabel(book.getPublisher());
					publisher.setPreferredSize(d);
					resultBox.add(publisher);
					break;
				case 5 : 
					author = new JLabel(book.getAuthor());
					author.setPreferredSize(d);
					resultBox.add(author);
					break;
			}
		}
		
		
		contentBox.add(resultBox, BorderLayout.CENTER);
		/*
		 * 대기중이라면 대여하기 버튼 노출 
		 * 대여중이라면 반납하기 노출
		 * 반납중이라면 검수하기 버튼 노출
		 * 검수중이라면 대기하기 버튼 노출하기
		 */
		actionBtn.setVisible(true);
		String statusTxt = null;
		switch (book.getStatus()) {
			case READY : statusTxt = "대여하기";  break;
			case RENTAL : statusTxt = "반납하기"; break;
			case RETURN : statusTxt = "검수하기"; break;
			case CHECKING : statusTxt = "대기하기"; break;
		}
		actionBtn.setText(statusTxt); 
		updateUI();
	}	
}