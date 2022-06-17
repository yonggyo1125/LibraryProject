package com.codefty.library.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.codefty.library.book.*;
import com.codefty.library.common.*;

/**
 * ���� �뿩, �ݳ� UI
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
		
		/** ��� ���� S */
		topBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topBox.setBorder(new TitledBorder("���� ��ȸ"));
		topBox.setBackground(Color.WHITE);
		
		serialNumBox = new JTextField(15);
		searchBtn = new JButton("��ȸ�ϱ�");
		searchBtn.addActionListener(this);
		topBox.add(serialNumBox);
		topBox.add(searchBtn);
		add(topBox, BorderLayout.NORTH);
		/** ��� ���� E */
		
		/** ���� ���� S */
		contentBox = new JPanel(new BorderLayout());
		contentBox.setBackground(Color.WHITE);
		add(contentBox, BorderLayout.CENTER);
		/** ���� ���� E */
		
		/** ��ư �߰� */
		actionBtn = new JButton();
		add(actionBtn, BorderLayout.SOUTH);
		actionBtn.setVisible(false);
		actionBtn.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == searchBtn) {
				// ��ȸ ��� ������Ʈ
				updateSearchResult();
			} else if (e.getSource() == actionBtn) {
				
				JButton btn = (JButton)e.getSource();
				String status = btn.getText();
				
				int result = JOptionPane.showConfirmDialog(this, "���� �����Ͻðڽ���? - " + status , "Ȯ��", JOptionPane.YES_NO_OPTION);
				if (result == 1) {
					return;
				}
				
				/** ���� �뿩, �ݳ� ����ڰ� ó���ϴ� �κ� ... */
				switch(status) {
					case "����ϱ�":
						break;
					case "�뿩�ϱ�":
						break;
					case "�ݳ��ϱ�":
						break;
					case "�˼��ϱ�":
						break;
				}
				
				// ó�� �Ϸ� �� ���� ���� ����
				updateSearchResult();
			}
		} catch (CommonException _e) {
			_e.printStackTrace();
		}
	}
	
	/**
	 * ��ȸ��� ������Ʈ
	 */
	private void updateSearchResult() {		
		String serialNumStr = serialNumBox.getText();
		if (serialNumStr.isBlank()) {
			throw new CommonException(this, "��ȸ�� �Ϸù�ȣ�� �Է��ϼ���.");
		}
		
		long serialNum;
		try {
			serialNum = Long.parseLong(serialNumStr.trim());
		} catch (NumberFormatException e) {
			throw new CommonException(this, "�Ϸù�ȣ�� ���ڸ� �Է��ϼ���.");
		}
	
		// �Ϸù�ȣ�� ���� ��ȸ
		Book book = BookService.getInstance().get(serialNum);
		
		if (resultBox != null) {
			contentBox.remove(resultBox);
		}
		
		int width = getWidth(); // ���� �ʺ�
		
		resultBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		resultBox.setBackground(Color.WHITE);
		String[] label = { "�Ϸù�ȣ", "�뿩����", "�뿩�Ⱓ", "������", "���ǻ�", "����" };
	
		JLabel[] lbs = new JLabel[6];
		for(int i = 0; i < lbs.length; i++) {
			lbs[i] = new JLabel(label[i]);
			lbs[i].setPreferredSize(new Dimension(80, 20));
			resultBox.add(lbs[i]);
			Dimension d = new Dimension(width - 90, 20);
			
			String rentalPeriods; // ��Ż�Ⱓ
			
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
					// �뿩, �ݳ� ��� ���� ����ڰ� ó���� �ּ���.
					if (book.getStatus() == RentalStatus.READY) {
						rentalPeriods = "�뿩�Ϸ� ���� " + book.getRentalDays() + "�ϰ�";
					} else {
						rentalPeriods = "��Ż �Ⱓ...";
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
		 * ������̶�� �뿩�ϱ� ��ư ���� 
		 * �뿩���̶�� �ݳ��ϱ� ����
		 * �ݳ����̶�� �˼��ϱ� ��ư ����
		 * �˼����̶�� ����ϱ� ��ư �����ϱ�
		 */
		actionBtn.setVisible(true);
		String statusTxt = null;
		switch (book.getStatus()) {
			case READY : statusTxt = "�뿩�ϱ�";  break;
			case RENTAL : statusTxt = "�ݳ��ϱ�"; break;
			case RETURN : statusTxt = "�˼��ϱ�"; break;
			case CHECKING : statusTxt = "����ϱ�"; break;
		}
		actionBtn.setText(statusTxt); 
		updateUI();
	}	
}