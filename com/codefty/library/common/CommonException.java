package com.codefty.library.common;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * 공용 예외
 * 
 * GUI 상세 예외 내용을 표기해야 하므로 팝업 형태로 표기 
 * 
 * @author YONGGYO
 */
public class CommonException extends RuntimeException {
	public CommonException(Component parentComponent, String message) {
		super(message);
		
		JOptionPane.showMessageDialog(parentComponent, message, "알림", JOptionPane.ERROR_MESSAGE);
	}
}
