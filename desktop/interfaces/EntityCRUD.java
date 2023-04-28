/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.interfaces;

import java.util.List;

/**
 *
 * @author msi
 */
public interface EntityCRUD <T> {
    public void AddEntity(T t);
    public List<T> display();
    
    
}

