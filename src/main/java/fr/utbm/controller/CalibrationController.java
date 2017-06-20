package fr.utbm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.utbm.entity.Location;
import fr.utbm.service.CalibrationService;

@Controller
public class CalibrationController {
	
	private CalibrationService calibrationService;
	
	@RequestMapping( "calibration")
	@ResponseBody
	public String  calibration(HttpServletRequest request){
		String result = "Try again";
		String x = request.getParameter("x");
		String y = request.getParameter("y");
		String mac = request.getParameter("mac");
		Location loc = new Location(Double.parseDouble(x), Double.parseDouble(y));
		calibrationService.saveOrCheckLocation(x,y);
		calibrationService.sendPositionRequest(mac);
		Boolean verified = calibrationService.checkAndSaveSample(loc);
		if (verified)
			result = "Ok";
		return result;
	}
	
	@RequestMapping("saveRSSI")
	public void saveRSSI(HttpServletRequest request){
		String ap_mac = request.getParameter("ap");
		int sample_nb = Integer.parseInt(request.getParameter("nb"));
		double val = Double.parseDouble(request.getParameter("val"));
		calibrationService.saveTempRecord(ap_mac,sample_nb,val);
	}
}
