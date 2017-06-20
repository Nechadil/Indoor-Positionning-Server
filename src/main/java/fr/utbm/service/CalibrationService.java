package fr.utbm.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import antlr.collections.List;
import fr.utbm.entity.AccessPoint;
import fr.utbm.entity.Location;
import fr.utbm.entity.RSSIRecord;
import fr.utbm.entity.TempRecord;
import fr.utbm.util.HTTPConnection;
import fr.utbm.util.HibernateDao;

@Service
public class CalibrationService {
	private HibernateDao dao;
	private static final String[] apIP = new String[]{"ip1:port1","ip2:port2","ip3:port3"};
	
	public CalibrationService(){dao = new HibernateDao();}
	
	public void getLocation(String x, String y){
		
	}
	public void saveOrCheckLocation(String x, String y){
		Location loc = new Location(Double.parseDouble(x),Double.parseDouble(y));
		//The location should only be saved when the location doesn't  exist
		if (dao.getLocation(Double.parseDouble(x), Double.parseDouble(y)) == null)
				dao.saveLocation(loc);
	}
	
	public void sendPositionRequest(String mac){
		String urlParameters = "mac="+mac;
		for(String ip:apIP){
			ip = "http://"+ip;
			//the result includes:
			String result = HTTPConnection.executePost(ip, urlParameters);
		}
	}
	public void saveRSSI(Location loc,String mac,Double val){
		AccessPoint ap = dao.getAccessPoint(mac);
		RSSIRecord record = new RSSIRecord(loc, ap, val);
		dao.saveRssiRecord(record);
	}
	public void saveTempRecord(String ap_mac, int sample_nb, double val){
		AccessPoint ap = dao.getAccessPoint(ap_mac);
		TempRecord temp = dao.getTempRecord(ap.getId(), val);
		if (temp == null){
			dao.saveTempRecord(new TempRecord(ap, sample_nb, val));
		} else{
			temp.setOccurence(temp.getOccurence()+sample_nb);
			dao.saveTempRecord(temp);
		}
	}
	public Boolean checkAndSaveSample(Location loc){
		Boolean checked = false;
		ArrayList<AccessPoint> list = (ArrayList<AccessPoint>) dao.getAccessPoint();
		for(AccessPoint ap:list){
			TempRecord temp = dao.getTempRecord(ap.getId());
			if(temp.getOccurence()>=5){
				checked = true;
				dao.saveRssiRecord(new RSSIRecord(loc, ap, temp.getVal()));
			}
		}
		return checked;
	}
}
