/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
/**
 *
 * @author USER
 */
public class MyConnection {
        private String url="jdbc:mysql://localhost:3306/3a41";
    private String login="root";
    private String pwd="";
    Connection cnx ;
    public static MyConnection instance;

    private MyConnection() {
        try{
        cnx = DriverManager.getConnection(url, login ,pwd);
            System.out.println("connexion établie");
    }
        catch (SQLException ex ){
            System.out.println(ex.getMessage());
        }
  
}

    public Connection getCnx() {
        return cnx;
    }

    public static MyConnection getInstance() {
        if(instance==null)
        {instance = new MyConnection();
        }
        return instance;
    }
    
}
