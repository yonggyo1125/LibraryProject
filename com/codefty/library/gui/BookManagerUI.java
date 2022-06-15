package com.codefty.library.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * ���� ���, ���� UI
 * �������, ���� ���/���� 
 * @author YONGGYO
 *
 */
public class BookManagerUI extends JPanel implements ActionListener {
	JButton listMenu, registMenu; 
	JPanel menuBox, contentBox;
	
	public BookManagerUI() {
		super(new BorderLayout());
			
		/** ��� S */
		menuBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		listMenu = new JButton("�������");
		registMenu = new JButton("�������");
		listMenu.addActionListener(this);
		registMenu.addActionListener(this);
		
		menuBox.add(listMenu);
		menuBox.add(registMenu);
		menuBox.setBackground(Color.WHITE);
		
		add(menuBox, BorderLayout.NORTH);
		/** ��� E */		
		
		/** ���� ���� - �⺻���� ������� S */
		contentBox = new BookListUI();
		add(contentBox, BorderLayout.CENTER);
		/** ���� ���� E */
		
	}
	
	/** 
	 * ��ܿ� ���� ���, ��� ��ư �߰�
	 * ��ư Ŭ���� ���� �κ��� ����ǵ��� ó��
	 *  
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		/* ���� ��� ���� ����� ���� �߰� */ 
		if (contentBox != null) {
			remove(contentBox);
		}
		
		if (e.getSource() == registMenu) { // ���� ��� 
			contentBox = new BookFormUI();
		} else { // ���� ���
			contentBox = new BookListUI();
		}
		
		add(contentBox, BorderLayout.CENTER);
		
		/** ������Ʈ�� ��ü�� ���� ȭ�鿡 �ٽ� �׷���� �Ѵ�. */
		revalidate(); 
		repaint();
	}
}
