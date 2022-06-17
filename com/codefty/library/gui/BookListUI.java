package com.codefty.library.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.*;

import com.codefty.library.common.*;
import com.codefty.library.search.*;

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
	JCheckBox[] statusSeach;
	JScrollPane searchScrollPane;
	
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
		
		searchField = new JTextField(15);
		topBox.add(searchField);
		
		searchBtn = new JButton("�˻�");
		searchBtn.setPreferredSize(new Dimension(65, 25));
		searchBtn.addActionListener(this);
		topBox.add(searchBtn);
		
		/** �뿩 ���� �˻� �߰� S */
		RentalStatus[] statuses = RentalStatus.values();
		statusSeach = new JCheckBox[statuses.length];
		for (int i = 0; i < statuses.length; i++) {
			statusSeach[i] = new JCheckBox(statuses[i].getStatusStr());
			statusSeach[i].setBackground(Color.WHITE);
			topBox.add(statusSeach[i]);
		}
		/** �뿩 ���� �˻� �߰� E */
		
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
		
			try {
				// �˻� ���� �� Ű���� ����
				String sopt = (String)searchOpts.getSelectedItem();
				String skey = searchField.getText();
				
				// ���õ� �뿩 ����
				ArrayList<String> selectedStatus = new ArrayList<>();
				for(JCheckBox chk : statusSeach) {
					if (chk.isSelected()) {
						selectedStatus.add(chk.getText());
					}
				} 
				
				// ���� ��� �˻� ó��  
				String[][] searchData = SearchService.getInstance()
																.search(sopt, skey, selectedStatus)
																.toArray();
				
				// �˻� ��� ���
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
		if (searchScrollPane != null) {
			contentBox.remove(searchScrollPane);
		}
		
		String[] headers = {"�Ϸù�ȣ", "������", "���ǻ�", "����", "����"};
		JTable searchResults = new JTable(searchData, headers);
		
		/** �� ��� ���� S */
		DefaultTableCellRenderer  render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(JLabel.CENTER);
		Enumeration<TableColumn> columns = searchResults.getColumnModel().getColumns();
		while(columns.hasMoreElements()) {
			TableColumn column = columns.nextElement();
			column.setCellRenderer(render);
		}
		/** �� ��� ���� E */
		
		/** ���̺� Ŭ�� �̺�Ʈ ó�� S */
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
		/** ���̺� Ŭ�� �̺�Ʈ ó�� E */
		searchScrollPane = new JScrollPane(searchResults, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentBox.add(searchScrollPane, BorderLayout.CENTER);
		updateUI();
	}
}
