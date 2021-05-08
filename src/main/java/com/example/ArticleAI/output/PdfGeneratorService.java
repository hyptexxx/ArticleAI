package com.example.ArticleAI.output;

import com.example.ArticleAI.dto.UtmoUserDto;
import com.example.ArticleAI.output.params.PdfParams;
import com.example.ArticleAI.repository.UserRepository;
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
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PdfGeneratorService {

    private final UserRepository userRepository;

    /**
     * Path to resources/uploadFiles.
     */
    private final String ROOT_PATH = System.getProperty("catalina.home") + File.separator +
            "webapps" + File.separator +
            "ROOT" + File.separator +
            "resources" + File.separator +
            "uploadedFiles";

    public File generate(String barcodeText, Integer userId, Integer publicationId, double actualityPercent) {

        UtmoUserDto utmoUserDto = userRepository.getUserById(userId);

        OutputStream file = null;
        File resultFile = new File(ROOT_PATH + File.separator + publicationId + "_MGOTU_CERT.pdf");
        final String qrPath = generateQRCodeImage(barcodeText).getAbsolutePath();
        try {
            final Document document = new Document();

            file = new FileOutputStream(resultFile);
            PdfWriter.getInstance(document, file);
            document.open();

            Image img = Image.getInstance(getClassPathResourcePath(PdfParams.HEADER_IMAGE));
            img.scalePercent(((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100);
            document.add(img);

            appendText(document, "Personal key:");

            img = Image.getInstance(qrPath);
            img.scalePercent(30F);
            document.add(img);

            appendText(document, "Actuality: " + actualityPercent + "%");
            appendText(document, "Upload date: " + new SimpleDateFormat("yyyy-MM-dd")
                    .format(new Date()));
            appendText(document, utmoUserDto.getFio());
            appendText(document, utmoUserDto.getInfo());
            appendText(document, barcodeText);

            document.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultFile;
    }

    @SneakyThrows
    private void appendText(Document document, String appendText) {
        final BaseFont times =
                BaseFont.createFont(getClassPathResourcePath(PdfParams.FONT_TIMES_NEW_ROMAN),
                        "cp1251", BaseFont.EMBEDDED);

        document.add(new Paragraph(appendText, new Font(times, 14)));
    }

    @SneakyThrows
    private File generateQRCodeImage(String barcodeText) {
        final QRCodeWriter barcodeWriter = new QRCodeWriter();
        final BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 500, 500);

        final BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        final File outputfile = new File("image.jpg");

        ImageIO.write(bufferedImage, "jpg", outputfile);

        return outputfile;
    }

    private String getClassPathResourcePath(String fileName) {
        return new File(
                PdfGeneratorService.class.getClassLoader()
                        .getResource(fileName)
                        .getFile()
        ).getAbsolutePath();
    }
}
