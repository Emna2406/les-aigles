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
public class Consultation {
      private int id;
    private int patient_id ;
    private int medecin_id;
    private String datedeconsultation;
            private String image;

    public Consultation(int id, int patient_id, int medecin_id, String datedeconsultation, String image) {
        this.id = id;
        this.patient_id = patient_id;
        this.medecin_id = medecin_id;
        this.datedeconsultation = datedeconsultation;
        this.image = image;
    }

    public Consultation(int patient_id, int medecin_id, String datedeconsultation, String image) {
        this.patient_id = patient_id;
        this.medecin_id = medecin_id;
        this.datedeconsultation = datedeconsultation;
        this.image = image;
    }

    public Consultation() {
    }

    public Consultation(int id) {
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

    public String getDatedeconsultation() {
        return datedeconsultation;
    }

    public void setDatedeconsultation(String datedeconsultation) {
        this.datedeconsultation = datedeconsultation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    @Override
    public String toString() {
        return "Consultation{" + "id=" + id + ", patient_id=" + patient_id + ", medecin_id=" + medecin_id + ", datedeconsultation=" + datedeconsultation + ", image=" + image + '}';
    }

    
}
