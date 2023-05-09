/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.entities;

import java.sql.Date;

/**
 *
 * @author YOSR
 */
public class Reservation {
      private int id;
        private int id_medecin;
          private int id_patient;
            private int id_chambre ;
              private Date date_deb;
              private String prix ;
               

    public Date getDate_deb() {
        return date_deb;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public void setDate_deb(Date date_deb) {
        this.date_deb = date_deb;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_medecin() {
        return id_medecin;
    }

    public void setId_medecin(int id_medecin) {
        this.id_medecin = id_medecin;
    }

    public int getId_patient() {
        return id_patient;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    public int getId_chambre() {
        return id_chambre;
    }

    public void setId_chambre(int id_chambre) {
        this.id_chambre = id_chambre;
    }

   

    public Reservation() {
    }
    
  public Reservation(int id_medecin, int id_patient, int id_chambre, Date date_deb) {
        this.id_medecin = id_medecin;
        this.id_patient = id_patient;
        this.id_chambre = id_chambre;
        this.date_deb = date_deb;
    }

    public Reservation(int id, int id_medecin, int id_patient, int id_chambre, Date date_deb) {
        this.id = id;
        this.id_medecin = id_medecin;
        this.id_patient = id_patient;
        this.id_chambre = id_chambre;
        this.date_deb = date_deb;
    }

    public Reservation(int id, int id_medecin, int id_patient, int id_chambre, Date date_deb, String prix) {
        this.id = id;
        this.id_medecin = id_medecin;
        this.id_patient = id_patient;
        this.id_chambre = id_chambre;
        this.date_deb = date_deb;
        this.prix = prix;
    }

    public Reservation(int id_medecin, int id_patient, int id_chambre, Date date_deb, String prix) {
        this.id_medecin = id_medecin;
        this.id_patient = id_patient;
        this.id_chambre = id_chambre;
        this.date_deb = date_deb;
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", id_medecin=" + id_medecin + ", id_patient=" + id_patient + ", id_chambre=" + id_chambre + ", date_deb=" + date_deb + ", prix=" + prix + '}';
    }

  
   
   

}
