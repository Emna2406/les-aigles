/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.interfaces;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author karra
 */
public interface EntityCRUD<T> {
    public void addEntity(T t);
    public List<T> display();
    ////////////////////////////////////////
     public void ajouter(T t) throws SQLException;
    public void supprimer(T t) throws SQLException;
    public void modifier(T t) throws SQLException;
    public List<T> afficher() throws SQLException;
   
   
}