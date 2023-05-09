/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.entities;

import java.sql.Date;

/**
 *
 * @author tha3leb
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author tha3leb
 */
public class Visite {
     private int idvisite;
    private String nomvisiteur ,nompatient;
     private Date date;
     
    private String numerotele;
    private String message;
       private int patient_id;

    public Visite(int idvisite, String nomvisiteur, String nompatient, Date date, String numerotele, String message, int patient_id) {
        this.idvisite = idvisite;
        this.nomvisiteur = nomvisiteur;
        this.nompatient = nompatient;
        this.date = date;
        this.numerotele = numerotele;
        this.message = message;
        this.patient_id = patient_id;
    }

    public Visite(String nomvisiteur, String nompatient, Date date, String numerotele, String message, int patient_id) {
        this.nomvisiteur = nomvisiteur;
        this.nompatient = nompatient;
        this.date = date;
        this.numerotele = numerotele;
        this.message = message;
        this.patient_id = patient_id;
    }

    public Visite(int idvisite) {
        this.idvisite = idvisite;
    }


    public int getIdvisite() {
        return idvisite;
    }

    public void setIdvisite(int idvisite) {
        this.idvisite = idvisite;
    }

    public String getNomvisiteur() {
        return nomvisiteur;
    }

    public void setNomvisiteur(String nomvisiteur) {
        this.nomvisiteur = nomvisiteur;
    }

    public String getNompatient() {
        return nompatient;
    }

    public void setNompatient(String nompatient) {
        this.nompatient = nompatient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNumerotele() {
        return numerotele;
    }

    public void setNumerotele(String numerotele) {
        this.numerotele = numerotele;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    @Override
    public String toString() {
        return "Visite{" + "idvisite=" + idvisite + ", nomvisiteur=" + nomvisiteur + ", nompatient=" + nompatient + ", date=" + date + ", numerotele=" + numerotele + ", message=" + message + ", patient_id=" + patient_id + '}';
    }

  
           
}

