/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.entities;

/**
 *
 * @author YOSR
 */
public class Chambre {
        private int id;
      //    private int service_id;
    private String num;
    private String capacite;
    private String prix;
    private String etat;

    public Chambre( int id ) {
        this.id = id ;
        
       
    }
  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   /* public int getId_service() {
        return service_id;
    }

    public void setId_service(int id_service) {
        this.service_id = id_service;
    }*/

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCapacite() {
        return capacite;
    }

    public void setCapacite(String capacite) {
        this.capacite = capacite;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Chambre(int id,  String num, String capacite, String prix, String etat) {
        this.id = id;
       
        this.num = num;
        this.capacite = capacite;
        this.prix = prix;
        this.etat = etat;
    }

   public Chambre( String num, String capacite, String prix, String etat) {
       
        this.num = num;
        this.capacite = capacite;
        this.prix = prix;
        this.etat = etat;
    }

    

   

    @Override
    public String toString() {
        return "Chambre{" + "id=" + id  +  ", num=" + num + ", capacite=" + capacite + ", prix=" + prix + ", etat=" + etat + '}';
    }

   

  

    

    public Chambre() {
    }
    
}
