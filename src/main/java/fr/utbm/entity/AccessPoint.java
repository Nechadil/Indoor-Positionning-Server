/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.utbm.entity;

import javax.persistence.Access;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "access_points")
public class AccessPoint {
    @Id@GeneratedValue
    private int id;
    private String mac_adr;
    private int location_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMac_adr() {
        return mac_adr;
    }

    public void setMac_adr(String mac_adr) {
        this.mac_adr = mac_adr;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }
    
    

    
}
