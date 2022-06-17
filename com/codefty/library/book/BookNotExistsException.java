package com.codefty.library.book;

import com.codefty.library.common.CommonException;
import java.awt.Component;

/**
 *  ������ �������� ������ ����
 * 
 * @author YONGGYO
 */
public class BookNotExistsException extends CommonException {
	public BookNotExistsException(Component parentComponent) {
		this(parentComponent, "��ϵ��� ���� ���� �Դϴ�.");
	}
	
	public BookNotExistsException(Component parentComponent, String message) {
		super(parentComponent, message);
		
	}
}
