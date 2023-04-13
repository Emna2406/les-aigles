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
public class Certificat {
     private int id;
    private int patient_id ;
    private int medecin_id;
    private String datedeb;
        private String datefin;
        private String image;

    public Certificat(int id, int patient_id, int medecin_id, String datedeb, String datefin, String image) {
        this.id = id;
        this.patient_id = patient_id;
        this.medecin_id = medecin_id;
        this.datedeb = datedeb;
        this.datefin = datefin;
        this.image = image;
    }

    public Certificat(int patient_id, int medecin_id, String datedeb, String datefin, String image) {
        this.patient_id = patient_id;
        this.medecin_id = medecin_id;
        this.datedeb = datedeb;
        this.datefin = datefin;
        this.image = image;
    }

    public Certificat() {
    }

    public Certificat(int id) {
        this.id = id;
    }

   
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getMedecin_id() {
        return medecin_id;
    }

    public void setMedecin_id(int medecin_id) {
        this.medecin_id = medecin_id;
    }

    public String getDatedeb() {
        return datedeb;
    }

    public void setDatedeb(String datedeb) {
        this.datedeb = datedeb;
    }

    public String getDatefin() {
        return datefin;
    }

    public void setDatefin(String datefin) {
        this.datefin = datefin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Certificat{" + "id=" + id + ", patient_id=" + patient_id + ", medecin_id=" + medecin_id + ", datedeb=" + datedeb + ", datefin=" + datefin + ", image=" + image + '}';
    }
    
}
