import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BarcodeTest {
    @SneakyThrows
    @Test
    public void testQR() {
        generateQRCodeImage("qwe");
    }


    @SneakyThrows
    public static File generateQRCodeImage(String barcodeText) {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 500, 500);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        File outputfile = new File("image.jpg");
        ImageIO.write(bufferedImage, "jpg", outputfile);

        return outputfile;
    }


    @Test
    void qwe() {
        try {
            OutputStream file = new FileOutputStream(new File("Test.pdf"));

            Document document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            Image img = Image.getInstance(getClassPathResourcePath("util/header.jpg"));

            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);

            BaseFont times =
                    BaseFont.createFont(getClassPathResourcePath("util/times.ttf"), "cp1251", BaseFont.EMBEDDED);
            Paragraph p = new Paragraph("Personal key:", new Font(times, 14));

            document.add(img);
            document.add(p);

            img = Image.getInstance(generateQRCodeImage("123").getAbsolutePath());
            img.scalePercent(30F);


            document.add(img);
            p = new Paragraph("Actuality: 40%", new Font(times, 14));
            document.add(p);
            document.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getClassPathResourcePath(String fileName) {
        return new File(
               BarcodeTest.class.getClassLoader()
                        .getResource(fileName)
                        .getFile()
        ).getAbsolutePath();
    }
}
