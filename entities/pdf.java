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
import edu.connexion3a41.services.ServiceS;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 *
 * @author YOSR
 */
public class pdf {
     public void GeneratePdf(String filename, Service p, int id) throws FileNotFoundException, DocumentException, BadElementException, IOException, InterruptedException, SQLException {

        Document document = new Document() {
        };
        PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));
        document.open();

        ServiceS us = new ServiceS();
      
        document.add(new Paragraph("nom :" + p.getNom()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("description:" + p.getDescription()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("image :" + p.getImage()));
        document.add(new Paragraph("                      "));
       

        document.add(new Paragraph("---------------------------------------------------------------------------------------------------------------------------------- "));
        document.add(new Paragraph("                              Listes Services Santiana                     "));
        document.close();
        Process pro = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filename + ".pdf");
    }

    
}
