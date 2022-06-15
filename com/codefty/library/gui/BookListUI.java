package com.codefty.library.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * ���� ��� UI
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
		
		/** ���� �˻� ���� S */
		topBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topBox.setBorder(new TitledBorder("���� �˻�"));
		topBox.setBackground(Color.WHITE);
		
		String[] opts = {"���հ˻�", "������", "���ǻ�", "����"};
		searchOpts = new JComboBox<>(opts);
		topBox.add(searchOpts);
		
		searchField = new JTextField(20);
		topBox.add(searchField);
		
		searchBtn = new JButton("�˻��ϱ�");
		searchBtn.addActionListener(this);
		topBox.add(searchBtn);
		
		add(topBox, BorderLayout.NORTH);	
		/** ���� �˻� ���� E */
		
		/** �˻� ��� ��� ���� S */
		contentBox = new JPanel(new BorderLayout());
		add(contentBox, BorderLayout.CENTER);
		/** �˻� ��� ��� ���� S */
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchBtn) {
			
			// �ϱ� �ڵ�� �˻� ��� �۾��ڰ� ó���� �ּ���...
			String[][] searchData = { {"�׽�Ʈ1", "�׽�Ʈ2", "�׽�Ʈ3", "�׽�Ʈ4", "�׽�Ʈ5" } };
			
			showSearchResults(searchData);
		}
	}
	
	/**
	 * ���� �˻� ��� ��� 
	 * 
	 * @param searchData : �˻� ������
	 */
	private void showSearchResults(String[][] searchData) {
		if (searchResults != null) {
			remove(searchResults);
		}
		
		String[] headers = {"�Ϸù�ȣ", "������", "���ǻ�", "����", "����"};
		searchResults = new JTable(searchData, headers);
		searchResults.setEnabled(false);
		JScrollPane js = new JScrollPane(searchResults, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		contentBox.add(js, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
