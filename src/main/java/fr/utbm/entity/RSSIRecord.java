package fr.utbm.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="rssi")
public class RSSIRecord {
    
	@Id@GeneratedValue
	private int id;
	
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Location loc;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private AccessPoint ap;
    private double value;
    
    public RSSIRecord(Location loc, AccessPoint ap, Double value){
        this.loc = loc;
        this.ap = ap;
        this.value = value;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public AccessPoint getAp() {
		return ap;
	}
	public void setAp(AccessPoint ap) {
		this.ap = ap;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

   
    

}
