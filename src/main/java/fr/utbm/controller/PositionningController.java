package fr.utbm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.utbm.entity.Location;
import fr.utbm.service.CalibrationService;
import fr.utbm.service.PositionService;

@Controller
public class PositionningController {

	private PositionService positionService;
	private CalibrationService calibrationService;
	@RequestMapping( "locate")
	@ResponseBody
	public String locate(HttpServletRequest request){
		String result = "Try again";
		String mac_add = request.getParameter("mac");
		Boolean haveEnoughTempRecord = positionService.haveEnoughTempRecord(mac_add);
		if(!haveEnoughTempRecord){
			calibrationService.sendPositionRequest(mac_add);
			return result;
		}
		Location location = positionService.locate(mac_add);
		result = "x="+location.getX()+", y="+location.getY();
		return result;
	}
}
