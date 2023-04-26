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
public class Offre {
    private int id;
    private int nbrplaces;
    private String description;
    private int idservice;

    public Offre() {
    }
        public Offre(int id, int nbrplaces, String description, int idservice) {
        this.id = id;
        this.nbrplaces = nbrplaces;
        this.description = description;
        this.idservice = idservice;
    }

    public Offre(int nbrplaces, String description, int idservice) {
        this.nbrplaces = nbrplaces;
        this.description = description;
        this.idservice = idservice;
    }

    public Offre(String description, int nbrplaces, int idService) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbrplaces() {
        return nbrplaces;
    }

    public void setNbrplaces(int nbrplaces) {
        this.nbrplaces = nbrplaces;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdservice() {
        return idservice;
    }

    public void setIdservice(int idservice) {
        this.idservice = idservice;
    }



    @Override
    public String toString() {
        return "Offre{" + "id=" + id + ", nbrplaces=" + nbrplaces + ", description=" + description + ", idservice=" + idservice + '}';
    }
}
