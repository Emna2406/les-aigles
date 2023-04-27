/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.services;

import desktop.entities.Rating;
import desktop.tools.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author msi
 */
public class RatingService {

    Connection cnx;
    public RatingService() {
        cnx = MyConnection.getInstance().getCnx();
    }

    public void rateProduit(int id_produit, int rating) {
        // Update the rating value in the database
        String query ="INSERT INTO rating (id_produit, rating) VALUES (?, ?)";
        try {
            PreparedStatement ste = cnx.prepareStatement(query);
            ste.setInt(1, id_produit);
            ste.setInt(2, rating);
            ste.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Rating> getAll() throws SQLException {
        List<Rating> raating = new ArrayList<Rating>();
        String req = "select * from rating";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            System.out.println(rs);
            Rating c = new Rating
                    (rs.getInt("id"), rs.getInt("rating"),rs.getInt("id_produit"));

            raating.add(c);
            System.out.println();
        }
        return raating;
    }

}