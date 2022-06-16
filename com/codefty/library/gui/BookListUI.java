package com.codefty.library.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.*;

import com.codefty.library.common.*;

/**
 * ���� ��� UI
 * 
 * @author YONGGYO
 */
public class BookListUI extends JPanel implements ActionListener {
	JPanel wrapBox, topBox, contentBox;
	JComboBox<String> searchOpts;
	JTextField searchField;
	JButton searchBtn;
	JTable searchResults;
	
	public BookListUI() {
		super(new BorderLayout());
		setBackground(Color.WHITE);
		wrapBox = new JPanel(new BorderLayout());
		
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
		
		wrapBox.add(topBox, BorderLayout.NORTH);	
		/** ���� �˻� ���� E */
		
		/** �˻� ��� ��� ���� S */
		contentBox = new JPanel(new BorderLayout());
		contentBox.setBackground(Color.WHITE);
		wrapBox.add(contentBox, BorderLayout.CENTER);
		/** �˻� ��� ��� ���� S */
		
		add(wrapBox, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchBtn) {		
			// �˻� ���� �� Ű���� ����
			try {
				String sopt = (String)searchOpts.getSelectedItem();
				String skey = searchField.getText();
				// �˻�� ��� �ִ� ���� �˸� �޼��� 
				if (skey == null || skey.trim().equals("")) {
					throw new CommonException(this, "�˻� Ű���带 �Է��ϼ���");
				}
				
				/**
				 * �ϱ� �ڵ�� �˻� ��� �۾��ڰ� ó���� �ּ���
				 * 
				 *  sopt, skey ���� ������ ���� ������ �����ϴ� �޼��� �߰��Ͽ� showSearchResults�� �Ѱ� �ֽø� �˴ϴ�.
				 */
				String[][] searchData = { {"10000", "�׽�Ʈ2", "�׽�Ʈ3", "�׽�Ʈ4", "�׽�Ʈ5" },  {"10001", "�׽�Ʈ2", "�׽�Ʈ3", "�׽�Ʈ4", "�׽�Ʈ5" }  };
				
				showSearchResults(searchData);
			} catch (CommonException _e) {
				_e.printStackTrace();
			}
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
		
		/** �� ��� ���� S */
		DefaultTableCellRenderer  render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(JLabel.CENTER);
		Enumeration<TableColumn> columns = searchResults.getColumnModel().getColumns();
		while(columns.hasMoreElements()) {
			TableColumn column = columns.nextElement();
			column.setCellRenderer(render);
		}
		/** �� ��� ���� E */
		/** ���̺� ���� Ŭ�� �̺�Ʈ ó�� S */
		searchResults.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					JTable table = (JTable)e.getSource();
					long serialNum = Long.parseLong((String)table.getValueAt(table.getSelectedRow(), 0));
					
					// ���� ���� �������� ��ȯ 				
					remove(wrapBox);
					wrapBox = new BookFormUI(serialNum);
					add(wrapBox, BorderLayout.CENTER);
					updateUI();	
				} catch (NumberFormatException _e) {}
			}
		});
		/** ���̺� ���� Ŭ�� �̺�Ʈ ó�� E */
		
		JScrollPane js = new JScrollPane(searchResults, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentBox.add(js, BorderLayout.CENTER);
		updateUI();
	}
}
