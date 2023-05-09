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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author tha3leb
 */
public class Rdv {
    
     private int idrdv;
    private String nompatient;
     private Date date;
     
    private String numtel;
    private String message;
           
           private String adr;
                private int medecin_id;

    public Rdv(int idrdv) {
        this.idrdv = idrdv;
    }

    public Rdv(int idrdv, String nompatient, Date date, String numtel, String message, String adr, int medecin_id) {
        this.idrdv = idrdv;
        this.nompatient = nompatient;
        this.date = date;
        this.numtel = numtel;
        this.message = message;
        this.adr = adr;
        this.medecin_id = medecin_id;
    }

    public Rdv(String nompatient, Date date, String numtel, String message, String adr, int medecin_id) {
        this.nompatient = nompatient;
        this.date = date;
        this.numtel = numtel;
        this.message = message;
        this.adr = adr;
        this.medecin_id = medecin_id;
    }

   
     public Rdv() {
       
    }

   

   

    public int getIdrdv() {
        return idrdv;
    }

    public void setIdrdv(int idrdv) {
        this.idrdv = idrdv;
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

    public String getNumtel() {
        return numtel;
    }

    public void setNumtel(String numtel) {
        this.numtel = numtel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public int getMedecin_id() {
        return medecin_id;
    }

    public void setMedecin_id(int medecin_id) {
        this.medecin_id = medecin_id;
    }

    @Override
    public String toString() {
        return "Rdv{" + "idrdv=" + idrdv + ", nompatient=" + nompatient + ", date=" + date + ", numtel=" + numtel + ", message=" + message + ", adr=" + adr + ", medecin_id=" + medecin_id + '}';
    }

    
           
            
    
}

