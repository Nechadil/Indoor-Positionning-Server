/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.utbm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="maps")
public class Map {
    
	@Id@GeneratedValue
    private int id;
    private String description;
    private int px_width;
    private int px_height;
    private Double m_width;
    private Double m_height;
    private byte content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPx_width() {
        return px_width;
    }

    public void setPx_width(int px_width) {
        this.px_width = px_width;
    }

    public int getPx_height() {
        return px_height;
    }

    public void setPx_height(int px_height) {
        this.px_height = px_height;
    }

    public Double getM_width() {
        return m_width;
    }

    public void setM_width(Double m_width) {
        this.m_width = m_width;
    }

    public Double getM_height() {
        return m_height;
    }

    public void setM_height(Double m_height) {
        this.m_height = m_height;
    }

    public byte getContent() {
        return content;
    }

    public void setContent(byte content) {
        this.content = content;
    }

    
}
