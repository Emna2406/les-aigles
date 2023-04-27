/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.interfaces;

import desktop.entities.Rating;
import desktop.services.RatingService;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class StatistiqueController {


    @FXML
    private BarChart<String, Number> chart;

    private RatingService ratingService;


    public StatistiqueController() {
        ratingService = new RatingService();
    }


    private void initialize() throws SQLException {
        CategoryAxis xAxis = (CategoryAxis) chart.getXAxis();
        xAxis.setLabel("Product ID");

        NumberAxis yAxis = (NumberAxis) chart.getYAxis();
        yAxis.setLabel("Rating");

        List<Rating> ratings = ratingService.getAll();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Rating");
        for (Rating rating : ratings) {
            series.getData().add(new XYChart.Data<>(String.valueOf(rating.getId_produit()), rating.getRating()));
        }
        chart.getData().add(series);
    }
}
