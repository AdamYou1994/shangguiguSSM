package com.allen.crowd.funding.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.json.GsonFactoryBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.allen.crowd.funding.entity.ResultEntity;
import com.allen.crowd.util.Constant;
import com.allen.crowd.util.MD5Utils;
import com.google.gson.Gson;

@ControllerAdvice
public class ExceptionResolever {
	@ExceptionHandler(value = Exception.class)
	public ModelAndView catchException(Exception exception,HttpServletRequest request,HttpServletResponse response) throws Exception {
		boolean checkAsyncRequest = MD5Utils.checkAsyncRequest(request);
		if (checkAsyncRequest ) {
			String exceptionClassName = exception.getClass().getName();
			String message = Constant.EXCEPTION_MESSAGE_MAP.get(exceptionClassName);
			if (message == null) {
				message = "系统未知错误";
			}
			ResultEntity<String> failed = ResultEntity.failed(ResultEntity.NO_DATA, message);
			Gson gson = new Gson();
			String json = gson.toJson(failed);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(json);
			return null;
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception",exception);
		mav.setViewName("system-error");
		return mav;
	}
}
