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
	
	Integer[] rentalDuration = { 1, 2, 3, 4, 5, 6, 7, }; // �뿩 �Ⱓ
	
	/**
	 * ���� ��� UI
	 * 
	 */
	public BookFormUI() {
		super(new BorderLayout());
		
		wrapBox = new JPanel(new BorderLayout());
		
		/** ��� S */
		topBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topBox.add(new JLabel("���� ���"));
		topBox.setBackground(Color.WHITE);
		wrapBox.add(topBox, BorderLayout.NORTH);
		/** ��� E */
		
		/** ���� ���� S */
		contentBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		contentBox.setBackground(Color.WHITE);
		contentBox.add(new JLabel("������ : "));
		contentBox.add(bookTitle = new JTextField(45));
	
		contentBox.add(new JLabel("���ǻ� : "));
		contentBox.add(publisher = new JTextField(45));
		
		contentBox.add(new JLabel("����     : "));
		contentBox.add(author = new JTextField(45));
		
		contentBox.add(new JLabel("�뿩 ������ �� : "));
		rentalDays = new JComboBox<>(rentalDuration);
		rentalDays.setBackground(Color.WHITE);
		contentBox.add(rentalDays);
		contentBox.add(new JLabel("��"));
		
		wrapBox.add(contentBox, BorderLayout.CENTER);
		/** ���� ���� E */
		
		/** �ϴ� S */
		submitBtn = new JButton("����ϱ�");
		submitBtn.addActionListener(this);
		wrapBox.add(submitBtn, BorderLayout.SOUTH);
		/** �ϴ� E */
		
		add(wrapBox, BorderLayout.CENTER);
	}
	
	/**
	 * ���� ���� UI
	 * 
	 * @param serialNum : ���� �Ϸù�ȣ
	 */
	public BookFormUI(long serialNum) {
		this();
		
		this.serialNum = serialNum;
		
		/** ��� S */
		topBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topBox.add(new JLabel("���� �Ϸ� ��ȣ : " + serialNum));
		topBox.setBackground(Color.WHITE);
		wrapBox.add(topBox, BorderLayout.NORTH);
		/** ��� E */
		
		
		/** �ϴ� S */
		modifyBtn = new JButton("�����ϱ�");
		modifyBtn.addActionListener(this);
		wrapBox.add(modifyBtn, BorderLayout.SOUTH);
		/** �ϴ� E */
		
		updateUI();
		
		// ���� ���� ���� �Է� 
		try {
			updateBookInfo(serialNum);
		} catch (CommonException e) {
			e.printStackTrace();
			// ���� ������ �������� ������ �ٽ� ������� �̵� 
			moveToBookList();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			// �Է� ������ ��ȿ�� �˻�
			validateForm();
			
			// ���࿩�� 
			StringBuffer sb = new StringBuffer("���� "); 
			sb.append((e.getSource() == modifyBtn)?"����":"���");
			sb.append(" �Ͻðڽ��ϱ�?");
			String msg = sb.toString();
			 
			int result = JOptionPane.showConfirmDialog(this, msg, "Ȯ��", JOptionPane.YES_NO_OPTION);
			if (result == 1) { // ����� ���� �ߴ�
				return;
			}
			
			BookService bookSvc = new BookService();
			Book book = getBookInfo();
						
			if (e.getSource() == modifyBtn) { // �����ϱ� 
				bookSvc.update(book);
			} else { // ����ϱ�
				bookSvc.register(book);
			}
			
			// ��� �Ǵ� ������ �Ϸ� �Ǿ��ٸ� ���� ������� �̵�
			moveToBookList();
			
		} catch (CommonException _e) {
			_e.printStackTrace();
		}
	}
	
	/**
	 * ����ڰ� �Է��� ���� ���� ����
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
	 * ���� ���� ������ �ʵ忡 ���� ���� �Է�
	 * 
	 * @param serialNum
	 * @throws {CommonException} ���� ������ ���� ���
	 */
	private void updateBookInfo(long serialNum) {
		// ���� ����� ���� ������ �������� �κ��� ����Ͻô� ���� ������ �ּ���.
		/** 
	    Book book =  ...
		if (book == null) {
			throw CommonException(this, "��ϵ��� ���� �����Դϴ�.");
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
			fields.add("������");
		}
		
		if (book.getPublisher().isEmpty()) {
			fields.add("���ǻ�");
		}
		
		if (book.getAuthor().isEmpty()) {
			fields.add("����");
		}
		
		// ������ �׸��� �ִٸ�
		if (fields.size() > 0) {
			String missing = fields.stream().collect(Collectors.joining(","));
			String message = "�����׸��� ��� �Է��ϼ���(" + missing + ")";
			throw new CommonException(this, message);
		}
	}
	
	/**
	 * ���� ������� �̵� 
	 * 
	 */
	private void moveToBookList() {
		remove(wrapBox);
		wrapBox = new BookListUI();
		add(wrapBox, BorderLayout.CENTER);
		updateUI();
	}
}