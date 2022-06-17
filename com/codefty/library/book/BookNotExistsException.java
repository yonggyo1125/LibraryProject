package com.codefty.library.book;

import com.codefty.library.common.CommonException;
import java.awt.Component;

/**
 *  도서가 존재하지 않을때 예외
 * 
 * @author YONGGYO
 */
public class BookNotExistsException extends CommonException {
	public BookNotExistsException(Component parentComponent) {
		this(parentComponent, "등록되지 않은 도서 입니다.");
	}
	
	public BookNotExistsException(Component parentComponent, String message) {
		super(parentComponent, message);
		
	}
}
