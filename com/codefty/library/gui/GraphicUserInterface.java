package com.codefty.library.gui;

import javax.swing.*;
import java.awt.*;
/**
 * ����� �������̽� ���� 
 * 
 * @author YONGGYO
 */
public class GraphicUserInterface extends JFrame {
	public GraphicUserInterface() {
		super("�������� �ý���");
		
		JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);
		jtp.addTab("�뿩/�ݳ�����", new BookManagerUI());
		jtp.addTab("���� ����", new BookManagerUI());
		add(jtp, BorderLayout.CENTER);
		
		setSize(900, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
