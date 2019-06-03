package com.jzlc.util;

import java.math.BigDecimal;

/**
 * 系统常量工具类
 * @author Ivy
 *
 */
public class SystemConstant {
	// 防止丢失精度, 不要使用 new BigDecimal(double) 而使用new BigDecimal(String)
	/**默认值0*/
	public static final BigDecimal ZARO = new BigDecimal("0.0000");
	/**默认借款额度*/
	public static final BigDecimal INITIAL_BORROW_LIMIT = new BigDecimal( "2000.0000");
	/**验证码有效时间*/
	public static final int VERIFYCODE_VALIDATE_TIME = 5 * 60;
}
