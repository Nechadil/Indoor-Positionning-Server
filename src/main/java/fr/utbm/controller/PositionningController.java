package fr.utbm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PositionningController {

	@RequestMapping( "locate")
	@ResponseBody
	public String locate(HttpServletRequest request){
		String result = "Try again";
		String mac_add = request.getParameter("mac");
		return result;
	}
}
