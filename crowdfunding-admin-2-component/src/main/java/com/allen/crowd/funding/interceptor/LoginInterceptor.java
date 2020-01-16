package com.allen.crowd.funding.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.allen.crowd.funding.entity.Admin;
import com.allen.crowd.funding.entity.ResultEntity;
import com.allen.crowd.util.Constant;
import com.allen.crowd.util.MD5Utils;
import com.google.gson.Gson;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute(Constant.ATTR_NAME_LOGIN_ADMIN);
		if(admin == null) {
			boolean checkAsyncRequest = MD5Utils.checkAsyncRequest(request);
			if (checkAsyncRequest ) {
				ResultEntity<String> resultEntity = ResultEntity.failed(ResultEntity.NO_DATA, Constant.MESSAGE_ACCESS_DENIED);
				Gson gson = new Gson();
				String json = gson.toJson(resultEntity);
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(json);
				return false;
			}
			request.setAttribute(Constant.ATTR_NAME_MESSAGE, Constant.MESSAGE_ACCESS_DENIED);
			request.getRequestDispatcher("/WEB-INF/admin-login.jsp").forward(request, response);
			return false;
		}
		return true;
	}

}
