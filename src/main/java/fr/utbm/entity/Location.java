/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.utbm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Location {
    @Id@GeneratedValue
    private int Id;
    private double x;
    private double y;
    private int map_id;

    public Location(int Id, double x, double y, int map_id) {
        this.Id = Id;
        this.x = x;
        this.y = y;
        this.map_id = map_id;
    }
   

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getMap_id() {
        return map_id;
    }

    public void setMap_id(int map_id) {
        this.map_id = map_id;
    }
    
    
    
}
