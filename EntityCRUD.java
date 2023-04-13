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
public interface EntityCRUD<S> {
    public void addEntity(S s);
    public List<S> display();
    public void ajouter(S s) throws SQLException;
    public void supprimer(S s) throws SQLException;
    public void modifier(S s) throws SQLException;
    public List<S> afficher() throws SQLException;
    
}
