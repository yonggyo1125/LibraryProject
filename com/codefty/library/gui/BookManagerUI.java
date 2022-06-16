package com.codefty.library.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * 도서 등록, 수정 UI
 * 도서목록, 도서 등록/수정 
 * @author YONGGYO
 *
 */
public class BookManagerUI extends JPanel implements ActionListener {
	JButton listMenu, registMenu; 
	JPanel menuBox, contentBox;
	
	public BookManagerUI() {
		super(new BorderLayout());
			
		/** 상단 S */
		menuBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		listMenu = new JButton("도서목록");
		registMenu = new JButton("도서등록");
		listMenu.addActionListener(this);
		registMenu.addActionListener(this);
		
		menuBox.add(listMenu);
		menuBox.add(registMenu);
		menuBox.setBackground(Color.WHITE);
		
		add(menuBox, BorderLayout.NORTH);
		/** 상단 E */		
		
		/** 내용 영역 - 기본값은 도서목록 S */
		contentBox = new BookListUI();
		add(contentBox, BorderLayout.CENTER);
		/** 내용 영역 E */
		
	}
	
	/** 
	 * 상단에 도서 목록, 등록 버튼 추가
	 * 버튼 클릭시 내용 부분이 변경되도록 처리
	 *  
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		/* 기존 출력 내용 지우고 새로 추가 */ 
		if (contentBox != null) {
			remove(contentBox);
		}
		
		if (e.getSource() == registMenu) { // 도서 등록 
			contentBox = new BookFormUI();
		} else { // 도서 목록
			contentBox = new BookListUI();
		}
		
		add(contentBox, BorderLayout.CENTER);
		
		/** 컴포넌트를 교체한 경우는 화면에 다시 그려줘야 한다. */
		updateUI();
	}
}
