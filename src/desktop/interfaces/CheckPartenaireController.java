package desktop.interfaces;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import com.sun.javafx.iio.ImageStorage.ImageType;
import desktop.services.QrCode;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;

public class CheckPartenaireController implements Initializable {

    @FXML
    private ImageView qrCodeImageView; // ImageView to display the QR code image
    @FXML
    private ImageView qrCodeImageView1;

   @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateAndDisplayQRCode("https://www.google.com/search?q=serphadom&oq=serphadom&aqs=chrome.0.0i355i512j46i175i199i512j69i59j0i512l4.5878j0j15&sourceid=chrome&ie=UTF-8");
    }

    // Method to generate and display QR code image
   private void generateAndDisplayQRCode(String data) {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    int width = 300;
    int height = 300;
    String fileType = "png";

    BufferedImage bufferedImage = null;
    try {
        BitMatrix byteMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics();

        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        System.out.println("Success...");

    } catch (WriterException ex) {
        Logger.getLogger(QrCode.class.getName()).log(Level.SEVERE, null, ex);
    }

    qrCodeImageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
}

}

    /*
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String data1 = "Hello, World 1!"; // Update with your desired data for QR code 1
        String data2 = "Hello, World 2!"; // Update with your desired data for QR code 2

           try {
            Image qrCodeImage1 = generateQRCodeImage(data1);
            qrCodeImageView.setImage(qrCodeImage1);
        } catch (WriterException | IOException ex) {
            ex.printStackTrace();
        }

        // Generate QR code 2
        try {
            Image qrCodeImage2 = generateQRCodeImage(data2);
            qrCodeImageView1.setImage(qrCodeImage2);
        } catch (WriterException | IOException ex) {
            ex.printStackTrace();
        }
    }

      private Image generateQRCodeImage(String data) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        byte[] pixels = new byte[width * height * 3];
        int pos = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = bitMatrix.get(x, y) ? 0 : 255;
                pixels[pos++] = (byte) color;
                pixels[pos++] = (byte) color;
                pixels[pos++] = (byte) color;
            }
        }
        InputStream inputStream = new ByteArrayInputStream(pixels);
        Image qrCodeImage = new Image(inputStream, width, height, false, false);
        return qrCodeImage;
   
     }
*/

