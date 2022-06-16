package com.codefty.library.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.*;

import com.codefty.library.common.*;

/**
 * 도서 목록 UI
 * 
 * @author YONGGYO
 */
public class BookListUI extends JPanel implements ActionListener {
	JPanel wrapBox, topBox, contentBox;
	JComboBox<String> searchOpts;
	JTextField searchField;
	JButton searchBtn;
	JCheckBox[] statusSeach;
	JTable searchResults;
	
	public BookListUI() {
		super(new BorderLayout());
		setBackground(Color.WHITE);
		wrapBox = new JPanel(new BorderLayout());
		
		/** 도서 검색 영역 S */
		topBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topBox.setBorder(new TitledBorder("도서 검색"));
		topBox.setBackground(Color.WHITE);
		
		String[] opts = {"통합검색", "도서명", "출판사", "저자"};
		searchOpts = new JComboBox<>(opts);
		topBox.add(searchOpts);
		
		searchField = new JTextField(15);
		topBox.add(searchField);
		
		searchBtn = new JButton("검색");
		searchBtn.setPreferredSize(new Dimension(65, 25));
		searchBtn.addActionListener(this);
		topBox.add(searchBtn);
		
		/** 대여 상태 검색 추가 S */
		RentalStatus[] statuses = RentalStatus.values();
		statusSeach = new JCheckBox[statuses.length];
		for (int i = 0; i < statuses.length; i++) {
			statusSeach[i] = new JCheckBox(statuses[i].getStatusStr());
			statusSeach[i].setBackground(Color.WHITE);
			topBox.add(statusSeach[i]);
		}
		/** 대여 상태 검색 추가 E */
		
		wrapBox.add(topBox, BorderLayout.NORTH);	
		/** 도서 검색 영역 E */
		
		/** 검색 결과 출력 영역 S */
		contentBox = new JPanel(new BorderLayout());
		contentBox.setBackground(Color.WHITE);
		wrapBox.add(contentBox, BorderLayout.CENTER);
		/** 검색 결과 출력 영역 S */
		
		add(wrapBox, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchBtn) {		
		
			try {
				// 검색 조건 및 키워드 추출
				String sopt = (String)searchOpts.getSelectedItem();
				String skey = searchField.getText();
				// 검색어가 비어 있는 경우는 알림 메세지 
				if (skey == null || skey.trim().equals("")) {
					throw new CommonException(this, "검색 키워드를 입력하세요");
				}
				
				// 선택된 대여 상태
				ArrayList<String> selectedStatus = new ArrayList<>();
				for(JCheckBox chk : statusSeach) {
					if (chk.isSelected()) {
						selectedStatus.add(chk.getText());
					}
				} 
				
				/**
				 * 하기 코드는 검색 담당 작업자가 처리해 주세요
				 * 
				 *  sopt, skey 값을 가지고 도서 정보를 추출하는 메서드 추가하여 showSearchResults에 넘겨 주시면 됩니다.
				 */
				String[][] searchData = { {"10000", "테스트2", "테스트3", "테스트4", "테스트5" },  {"10001", "테스트2", "테스트3", "테스트4", "테스트5" }  };
				
				showSearchResults(searchData);
			} catch (CommonException _e) {
				_e.printStackTrace();
			}
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
		
		/** 셀 가운데 정렬 S */
		DefaultTableCellRenderer  render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(JLabel.CENTER);
		Enumeration<TableColumn> columns = searchResults.getColumnModel().getColumns();
		while(columns.hasMoreElements()) {
			TableColumn column = columns.nextElement();
			column.setCellRenderer(render);
		}
		/** 셀 가운데 정렬 E */
		/** 테이블 더블 클릭 이벤트 처리 S */
		searchResults.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					JTable table = (JTable)e.getSource();
					long serialNum = Long.parseLong((String)table.getValueAt(table.getSelectedRow(), 0));
					
					// 도서 수정 페이지로 전환 				
					remove(wrapBox);
					wrapBox = new BookFormUI(serialNum);
					add(wrapBox, BorderLayout.CENTER);
					updateUI();	
				} catch (NumberFormatException _e) {}
			}
		});
		/** 테이블 더블 클릭 이벤트 처리 E */
		
		JScrollPane js = new JScrollPane(searchResults, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentBox.add(js, BorderLayout.CENTER);
		updateUI();
	}
}
