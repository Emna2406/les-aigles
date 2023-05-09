/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.entities;

/**
 *
 * @author aouadh
 */
public class Ordonnance {
     private int id;
    private int consultation_id ;
    
    private String date;
    private String description;
    private String image;

    public Ordonnance(int id, int consultation_id, String date, String description, String image) {
        this.id = id;
        this.consultation_id = consultation_id;
        this.date = date;
        this.description = description;
        this.image = image;
    }

    public Ordonnance(int consultation_id, String date, String description, String image) {
        this.consultation_id = consultation_id;
        this.date = date;
        this.description = description;
        this.image = image;
    }

    public Ordonnance() {
    }

    public Ordonnance(int id) {
        this.id = id;
    }

   

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConsultation_id() {
        return consultation_id;
    }

    public void setConsultation_id(int consultation_id) {
        this.consultation_id = consultation_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Ordonnance{" + "id=" + id + ", consultation_id=" + consultation_id + ", date=" + date + ", description=" + description + ", image=" + image + '}';
    }
    
    
    
}
