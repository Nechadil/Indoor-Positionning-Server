package fr.utbm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.utbm.entity.AccessPoint;
import fr.utbm.entity.Location;
import fr.utbm.entity.RSSIRecord;
import fr.utbm.entity.TempRecord;
import fr.utbm.util.HibernateDao;

@Service
public class PositionService {
	private HibernateDao dao;
	public PositionService(){
		this.dao = new HibernateDao();
	}
	public Boolean haveEnoughTempRecord(String mac){
		Boolean result = true;
		ArrayList<AccessPoint> list = (ArrayList<AccessPoint>) dao.getAccessPoint();
		for(AccessPoint ap:list){
			TempRecord temp = dao.getTempRecord(ap.getId());
			if(temp.getOccurence()<5){
				result = false;
				return result;
			}
		}
		return result;
	}
	public Location locate(String mac){
		Location location = new Location();
		ArrayList<TempRecord> tempList = (ArrayList<TempRecord>)dao.getAllTempRecord();
		ArrayList<RSSIRecord> rssiList = (ArrayList<RSSIRecord>)dao.getAllRSSIRecord();
		ArrayList<Location> list = new ArrayList<Location>();
		int nb = 0;
		double x = 0;
		double y = 0;
		for(TempRecord temp: tempList){
			//For each tempRecord, get the nearest location according to the fingerprint
			Location loc = this.nearestLocation(temp, rssiList);
			x += loc.getX() * loc.getX();
			y += loc.getY() * loc.getY();
			nb++;
		}
		location.setX(Math.sqrt(x/nb));
		location.setY(Math.sqrt(y/nb));
		return location;
	}
	private Location nearestLocation(TempRecord temp, ArrayList<RSSIRecord> rssiList){
		//Set the distance to be a place far away
		Location location = new Location(1000,1000);
		//Set the difference between the real signal value and the signal strength in the fingerprint to be very large
		Double diff = (double) 1000;
		Double val = temp.getVal();
		AccessPoint ap = temp.getAp();
		for(RSSIRecord record:rssiList){
			if (ap == record.getAp()) {
				/*If for the same ap the difference between the real signal strength and the fingerprint 
				 * signal strength is smaller than the former one, the location will be set to the new one 
				 */
				if(Math.abs(record.getValue()-val)<diff)
					location = record.getLoc();
			}
		}
		return location;
	}
}
