package com.codefty.library.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * 도서 목록 UI
 * 
 * @author YONGGYO
 */
public class BookListUI extends JPanel implements ActionListener {
	JPanel topBox, contentBox;
	JComboBox<String> searchOpts;
	JTextField searchField;
	JButton searchBtn;
	JTable searchResults;
	
	public BookListUI() {
		super(new BorderLayout());
		setBackground(Color.WHITE);
		
		/** 도서 검색 영역 S */
		topBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topBox.setBorder(new TitledBorder("도서 검색"));
		topBox.setBackground(Color.WHITE);
		
		String[] opts = {"통합검색", "도서명", "출판사", "저자"};
		searchOpts = new JComboBox<>(opts);
		topBox.add(searchOpts);
		
		searchField = new JTextField(20);
		topBox.add(searchField);
		
		searchBtn = new JButton("검색하기");
		searchBtn.addActionListener(this);
		topBox.add(searchBtn);
		
		add(topBox, BorderLayout.NORTH);	
		/** 도서 검색 영역 E */
		
		/** 검색 결과 출력 영역 S */
		contentBox = new JPanel(new BorderLayout());
		add(contentBox, BorderLayout.CENTER);
		/** 검색 결과 출력 영역 S */
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchBtn) {
			
			// 하기 코드는 검색 담당 작업자가 처리해 주세요...
			String[][] searchData = { {"테스트1", "테스트2", "테스트3", "테스트4", "테스트5" } };
			
			showSearchResults(searchData);
		}
	}
	
	/**
	 * 도서 검색 결과 출력 
	 * 
	 * @param searchData : 검색 데이터
	 */
	private void showSearchResults(String[][] searchData) {
		if (searchResults != null) {
			remove(searchResults);
		}
		
		String[] headers = {"일련번호", "도서명", "출판사", "저자", "상태"};
		searchResults = new JTable(searchData, headers);
		searchResults.setEnabled(false);
		JScrollPane js = new JScrollPane(searchResults, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		contentBox.add(js, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
