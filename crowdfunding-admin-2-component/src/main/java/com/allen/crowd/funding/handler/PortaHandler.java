package com.allen.crowd.funding.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortaHandler {

	@RequestMapping("/index")
	public String showIndex() {
		return "index-page";
	}
}
