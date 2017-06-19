package fr.utbm.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.engine.spi.Mapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.utbm.entity.Map;

@Controller
public class Test {
	
	@RequestMapping( "/1")
	@ResponseBody
	public String test(HttpServletRequest request ){
		String x = request.getParameter("x");
		 System.out.println(x);
		return "xxx";
	}
}