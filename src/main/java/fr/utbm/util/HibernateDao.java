package fr.utbm.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import fr.utbm.entity.AccessPoint;
import fr.utbm.entity.Location;
import fr.utbm.entity.Map;
import fr.utbm.entity.RSSIRecord;
import fr.utbm.entity.TempRecord;

public class HibernateDao {
	private TransactionCallBack execTransactionProcess(ITransactionProcess itp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        TransactionCallBack reply = new TransactionCallBack();
        try {
            session.beginTransaction();
            reply = itp.exec(session);
            session.getTransaction().commit();
            reply.setStatus(true);
            return reply;
        } catch (HibernateException he) {
            he.printStackTrace();
            if (session.getTransaction() != null) {
                try {
                    session.getTransaction().rollback();
                } catch (HibernateException he2) {
                    he2.printStackTrace();
                }
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return reply;
    }
	 private class TransactionCallBack<T>{
	        private boolean status;
	        private List<T> results;
	        
	        TransactionCallBack () {
	            status = false;
	            results = new ArrayList<T>();
	        }
	        
	        boolean isStatus () {
	            return status;
	        }
	        
	        void setStatus (final boolean status) {
	            this.status = status;
	        }
	        
	        List<T> getResults () {
	            return results;
	        }
	        
	        void setResults (final List<T> results) {
	            this.results = results;
	        }
	    }
	    private interface ITransactionProcess{
	        TransactionCallBack exec(Session tr);
	    }
	    private boolean saveOrUpdate(Object... objs){
	        TransactionCallBack callBack = execTransactionProcess((session)->{
	            TransactionCallBack reply = new TransactionCallBack();
	            for(Object obj : objs){
	                session.saveOrUpdate(obj);
	            }
	            return reply;
	        });
	        return callBack!=null && callBack.isStatus();
	    }
	    private <T> List<T> getObjectList(Class<T> clazz){
	        TransactionCallBack callBack = execTransactionProcess((session)->{
	            TransactionCallBack reply = new TransactionCallBack<T>();
	            Query query = session.createQuery("from "+clazz.getSimpleName());
	            List<Object> results = query.list();
	            for(Object result : results){
	                if(clazz.isInstance(result))
	                    reply.getResults().add(result);
	            }
	            return reply;
	        });
	        return callBack.getResults();
	    }
	    
	    public boolean saveTempRecord(TempRecord temp){
	    	return saveOrUpdate(temp);
	    }
	    public TempRecord getTempRecord(int ap_id, double val){
	        TransactionCallBack callBack = execTransactionProcess((session)->{
	            TransactionCallBack reply = new TransactionCallBack<RSSIRecord>();
	            Query query = session.createQuery("from temp t where t.ap_id = :ap_id and t.val :=val");
	            query.setParameter("ap_id", ap_id);
	            query.setParameter("val", val);
	            List<Object> results = query.list();
	            for(Object result : results){
	                if(result instanceof RSSIRecord)
	                    reply.getResults().add(result);
	            }
	            return reply;
	        });
	        return callBack.getResults().isEmpty()?null:(TempRecord)callBack.getResults().get(0);
	    }

	    public TempRecord getTempRecord(int ap_id){
	        TransactionCallBack callBack = execTransactionProcess((session)->{
	            TransactionCallBack reply = new TransactionCallBack<RSSIRecord>();
	            Query query = session.createQuery("from temp t where t.ap_id = :ap_id");
	            query.setParameter("ap_id", ap_id);
	            List<Object> results = query.list();
	            for(Object result : results){
	                if(result instanceof RSSIRecord)
	                    reply.getResults().add(result);
	            }
	            return reply;
	        });
	        return callBack.getResults().isEmpty()?null:(TempRecord)callBack.getResults().get(0);
	    }
	    public boolean saveRssiRecord(RSSIRecord rssi){
	        return saveOrUpdate(rssi);
	    }

	    public List<RSSIRecord> getAllRSSIRecord(){
	        return getObjectList(RSSIRecord.class);
	    }
	    
	    public List<RSSIRecord> getRSSIRecord(Integer locationID){
	        if(locationID == null)
	            return getAllRSSIRecord();
	        
	        TransactionCallBack callBack = execTransactionProcess((session)->{
	            TransactionCallBack reply = new TransactionCallBack<RSSIRecord>();
	            Query query = session.createQuery("from rssi r where r.id = :id");
	            query.setParameter("id", locationID);
	            List<Object> results = query.list();
	            for(Object result : results){
	                if(result instanceof RSSIRecord)
	                    reply.getResults().add(result);
	            }
	            return reply;
	        });
	        return callBack.getResults();
	    }
	    
	    public List<Location> getLocations(){
	        return getObjectList(Location.class);
	    }

	    public boolean saveAccessPoint (final AccessPoint aps) {
	        return saveOrUpdate(aps);
	    }

	    public List<AccessPoint> getAccessPoint () {
	       return getObjectList(AccessPoint.class);
	    }

	    public boolean saveMap(final Map map){return saveOrUpdate(map);}

	    public Map getMap(int mapId){
	        TransactionCallBack callBack = execTransactionProcess((session)->{
	            TransactionCallBack reply = new TransactionCallBack<Map>();
	            Query query = session.createQuery("from maps m where m.id = :id");
	            query.setParameter("id", mapId);
	            List<Object> results = query.list();
	            for(Object result : results){
	                if(result instanceof Map)
	                    reply.getResults().add(result);
	            }
	            return reply;
	        });
	        return (callBack.getResults().isEmpty()?null:(Map)callBack.getResults().get(0));
	    }

	    public AccessPoint getAccessPoint(String apMacAddress) {
	        TransactionCallBack callBack = execTransactionProcess((session)->{
	            TransactionCallBack reply = new TransactionCallBack<AccessPoint>();
	            Query query = session.createQuery("from access_points ap where ap.mac_adr = :adr");
	            query.setParameter("adr", apMacAddress);
	            List<Object> results = query.list();
	            for(Object result : results){
	                if(result instanceof AccessPoint)
	                    reply.getResults().add(result);
	            }
	            return reply;
	        });
	        return callBack.getResults().isEmpty()?null:(AccessPoint)callBack.getResults().get(0);
	    }

	    public int saveLocation(Location location) {
	        TransactionCallBack callBack = execTransactionProcess((session)->{
	            TransactionCallBack<Integer> reply = new TransactionCallBack<Integer>();
	            Integer id = (Integer) session.save(location);
	            if(id!=null)
	                reply.results.add(id);
	            return reply;
	        });

	        if(callBack!=null && callBack.results.size()>0)
	            return (Integer)callBack.results.get(0);
	        return -1;
	    }

	    public Location getLocation(double x, double y) {
	        TransactionCallBack callBack = execTransactionProcess((session)->{
	            TransactionCallBack reply = new TransactionCallBack<Location>();
	            Query query = session.createQuery("from Location loc where loc.x = :x and loc.y=:y");
	            query.setParameter("x", x);
	            query.setParameter("y",y);
	            List<Object> results = query.list();
	            for(Object result : results){
	                if(result instanceof Location)
	                    reply.getResults().add(result);
	            }
	            return reply;
	        });
	        return (callBack.getResults().isEmpty()?null:(Location)callBack.getResults().get(0));
	    }
	    
	    public Location getLocation(int Location) {
	        TransactionCallBack callBack = execTransactionProcess((session)->{
	            TransactionCallBack reply = new TransactionCallBack<Location>();
	            Query query = session.createQuery("from Location loc where loc.id = :id");
	            query.setParameter("id", Location);
	            List<Object> results = query.list();
	            for(Object result : results){
	                if(result instanceof Location)
	                    reply.getResults().add(result);
	            }
	            return reply;
	        });
	        return (callBack.getResults().isEmpty()?null:(Location)callBack.getResults().get(0));
	    }
	    public void clearTemp(){
	    	//clear the values in the temp table
	    }

}
