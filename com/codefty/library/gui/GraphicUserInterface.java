package com.codefty.library.gui;

import javax.swing.*;
import java.awt.*;
/**
 * 사용자 인터페이스 구성 
 * 
 * @author YONGGYO
 */
public class GraphicUserInterface extends JFrame {
	public GraphicUserInterface() {
		super("도서관리 시스템");
		
		JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);
		jtp.addTab("대여/반납관리", new BookManagerUI());
		jtp.addTab("도서관리", new BookManagerUI());
		add(jtp, BorderLayout.CENTER);
		
		setSize(600, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		try {
			throw new com.codefty.library.common.CommonException(this, "테스트 메세지");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
}
