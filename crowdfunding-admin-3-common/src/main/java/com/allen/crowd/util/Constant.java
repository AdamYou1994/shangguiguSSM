package com.allen.crowd.util;

import java.util.HashMap;
import java.util.Map;

public class Constant {

	public static final String ATTR_NAME_MESSAGE = "MESSAGE";
	public static final String ATTR_NAME_LOGIN_ADMIN = "LOGIN-ADMIN";
	public static final String ATTR_NAME_PAGE_INFO = "PAGE-INFO";
	
	public static final String MESSAGE_LOGIN_FAILED = "登录的账号或密码不正确，请核对后在登陆";
	public static final String MESSAGE_CODE_INVALID = "MD5加密，明文无效！！！";
	public static final String MESSAGE_ACCESS_DENIED = "请登陆后再操作！！！";
	public static final String ATTR_NAME_LOGIN_ACCT_ALREADY_IN_USE = "用户名已被使用！！";
	
	public static final Map<String, String> EXCEPTION_MESSAGE_MAP = new HashMap<>();
	
	static {
		EXCEPTION_MESSAGE_MAP.put("java.lang.ArithmeticException", "数学运算时异常");
		EXCEPTION_MESSAGE_MAP.put("java.lang.RuntimeException", "运行时异常");
		EXCEPTION_MESSAGE_MAP.put("com.allen.crowd.funding.exception.LoginException", "用户登录时异常");
		EXCEPTION_MESSAGE_MAP.put("org.springframework.security.access.AccessDeniedException", "权限不足，拒绝访问");
	}
}
