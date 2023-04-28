/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.entities;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import edu.connexion3a41.services.ReservationService;
import edu.connexion3a41.services.ServiceS;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author YOSR
 */
public class pdfR {
      public void GeneratePdf(String filename, Reservation p, int id) throws FileNotFoundException, DocumentException, BadElementException, IOException, InterruptedException, SQLException {

        Document document = new Document() {
        };
        PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));
        document.open();

        ReservationService us = new ReservationService();
      
        document.add(new Paragraph("id_ chambre :" + p.getId_chambre()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("id_patient:" + p.getId_medecin()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("Id_patient :" + p.getId_patient()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("Date entreé :" + p.getDate_deb()));
        document.add(new Paragraph("                      "));
        
                // Calculating duration of reservation
        LocalDate debut = p.getDate_deb().toLocalDate();
        LocalDate fin = LocalDate.now();
        long nbJours = ChronoUnit.DAYS.between(debut, fin);

        document.add(new Paragraph("Durée de la réservation : " + nbJours + " jours"));
       

        document.add(new Paragraph("---------------------------------------------------------------------------------------------------------------------------------- "));
        document.add(new Paragraph("                              Listes Services Santiana                     "));
        document.close();
        Process pro = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filename + ".pdf");
    }
    
}
