/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.entities;

/**
 *
 * @author USER
 */
public class Candidat {
    private int idCand;
    private String nom;
    private String prenom;
    private String email;
    private String CV;
    private int idService;

    
    public Candidat() {
    } 
    
    
    public Candidat(String nom, String prenom, String email, String CV, int idService) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.CV = CV;
        this.idService = idService;
    }

    public Candidat(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

   /* public Candidat(String john_Doe, String johndoeexamplecom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    public Candidat(int idCand, String nom, String email, String CV) {
        this.idCand = idCand;
        this.nom = nom;
        this.email = email;
        this.CV = CV;
    }

    

    public int getIdCand() {
        return idCand;
    }

    public void setIdCand(int idCand) {
        this.idCand = idCand;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCV() {
        return CV;
    }

    public void setCV(String CV) {
        this.CV = CV;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public Candidat(int idCand, String nom, String prenom, String email, String CV, int idService) {
        this.idCand = idCand;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.CV = CV;
        this.idService = idService;
    }

    @Override
    public String toString() {
        return "Candidat{" + "idCand=" + idCand + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", CV=" + CV + ", idService=" + idService + '}';
    }

    
    
    
    
}
