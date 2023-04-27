/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.services;

import desktop.entities.Rating;
import desktop.tools.MyConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author msi
 */
public class Statistique  {


        private RatingService ratingService;
        private Connection cnx;
        public Statistique() {
            ratingService = new RatingService();
            cnx = MyConnection.getInstance().getCnx();
        }

        public void generateRatingChart() throws SQLException {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            List<Rating> ratings = ratingService.getAll(); // assuming you have a method to get all ratings
            for (Rating rating : ratings) {
                dataset.setValue((Number) rating.getRating(), "Rating", rating.getId_produit());
            }
            JFreeChart chart = ChartFactory.createBarChart(
                    "Product Ratings", // chart title
                    "Product ID", // x-axis label
                    "Rating", // y-axis label
                    dataset, // data
                    PlotOrientation.VERTICAL, // orientation
                    true, // include legend
                    true, // tooltips
                    false // urls
            );
            ChartFrame frame = new ChartFrame("Product Ratings", chart);
            frame.pack();
            RefineryUtilities.centerFrameOnScreen(frame);
            frame.setVisible(true);
        }


    }
