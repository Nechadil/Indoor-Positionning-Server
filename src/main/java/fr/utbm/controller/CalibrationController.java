package fr.utbm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.utbm.service.CalibrationService;

@Controller
public class CalibrationController {
	
	@RequestMapping( "calibration")
	public void getDataFromAp(HttpServletRequest request){
		String loc = request.getParameter("loc");
		String mac = request.getParameter("mac");
		Double val = Double.parseDouble(request.getParameter("val"));
		CalibrationService.recordSS(loc, mac, val);
	}
}
