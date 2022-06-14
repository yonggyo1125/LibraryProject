package com.codefty.library.common;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * ���� ����
 * 
 * GUI �� ���� ������ ǥ���ؾ� �ϹǷ� �˾� ���·� ǥ�� 
 * 
 * @author YONGGYO
 */
public class CommonException extends RuntimeException {
	public CommonException(Component parentComponent, String message) {
		super(message);
		
		JOptionPane.showMessageDialog(parentComponent, message, "�˸�", JOptionPane.ERROR_MESSAGE);
	}
}
