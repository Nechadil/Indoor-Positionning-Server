package fr.utbm.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="temp")
public class TempRecord {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AccessPoint getAp() {
		return ap;
	}

	public void setAp(AccessPoint ap) {
		this.ap = ap;
	}

	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}

	public int getOccurence() {
		return occurence;
	}

	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}

	@Id@GeneratedValue
	private int id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private AccessPoint ap;
	private double val;
	private int occurence;
	
	public TempRecord(AccessPoint ap, int occurence, Double val){
		this.ap = ap;
		this.val = val;
		this.occurence = occurence;
	}
}
