package com.objectify.models.transactions;

import com.objectify.datastore.SystemPointOfSales;
import com.objectify.exceptions.ItemNotFoundException;
import com.objectify.models.items.ShoppingCart;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class TransactionPDFGenerator extends Thread {

    private Transaction transaction;
    private String filename;

    public TransactionPDFGenerator(Transaction transaction, String filename) {
        this.transaction = transaction;
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            // String filename = "transaction_" + transaction.getTransactionId() + ".pdf";
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Create a new content stream for the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Set font and font size
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Add the logo
           PDImageXObject logo = PDImageXObject.createFromFile(".\\ObjectifyMainApp\\src\\resources\\images\\bmo.png", document);
           contentStream.drawImage(logo, 250, 710, 100, 100);

            // Add the headings
            contentStream.beginText();
            contentStream.newLineAtOffset(250, 700);
            contentStream.showText("Transaction Bill");
            contentStream.newLineAtOffset(-5, -20);
            contentStream.showText("Transaction ID: " + transaction.getTransactionId());
            contentStream.newLineAtOffset(-30, -20);
            contentStream.showText("Date Time: " + transaction.getDateTime());
            contentStream.newLineAtOffset(-150, -50);
            contentStream.showText("Description: " + transaction.getDescription());
            contentStream.endText();

            // Add the table
            float tableTopY = 600;
            float tableBottomY = 100;
            float tableWidth = page.getMediaBox().getWidth() - 100;
            float tableHeight = tableTopY - tableBottomY;
            float[] columnWidths = {50, 200, 70, 70, 70};
            float cellHeight = 20;

            // Set the graphics state for the table border
            PDExtendedGraphicsState graphicsState = new PDExtendedGraphicsState();
            graphicsState.setLineWidth(1.0f);
            contentStream.setGraphicsStateParameters(graphicsState);

            // Draw the table border
            contentStream.addRect(50, tableBottomY, tableWidth, tableHeight);
            contentStream.stroke();

            // Add the table headings
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.newLineAtOffset(60, tableTopY - cellHeight);
            contentStream.showText("No");
            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText("Item Name");
            contentStream.newLineAtOffset(columnWidths[1], 0);
            contentStream.showText("Quantity");
            contentStream.newLineAtOffset(columnWidths[2], 0);
            contentStream.showText("Unit Price");
            contentStream.newLineAtOffset(columnWidths[3], 0);
            contentStream.showText("Total Price");
            contentStream.endText();

            // Set the position of the first cell of the table
            float currentY = tableTopY - cellHeight - 25;
            // Add the items
            ShoppingCart cart = transaction.getShoppingCart();
            // Map<Product, Integer> items = cart.getItems();
            int i = 1;
            for (Map.Entry<Integer, Integer> entry : cart.getCartItems().entrySet()){
            // for (Product product : items.keySet()) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(60, currentY);
                contentStream.showText(String.valueOf(i));
                contentStream.newLineAtOffset(50, 0);
                try {
                    contentStream.showText(SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey()).getProductName());
                } catch (ItemNotFoundException e) {
                    contentStream.close();
                    throw new RuntimeException(e);
                }
                contentStream.newLineAtOffset(215, 0);
                contentStream.showText(String.valueOf(entry.getValue()));
                contentStream.newLineAtOffset(75, 0);
                try {
                    contentStream.showText(String.valueOf(SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey()).getProductPrice()));
                } catch (ItemNotFoundException e) {
                    contentStream.close();
                    throw new RuntimeException(e);
                }
                // contentStream.showText(String.valueOf(product.getProductPrice()));
                contentStream.newLineAtOffset(80, 0);
                try {
                    contentStream.showText(String.valueOf(SystemPointOfSales.getInstance().getStorageManager().searchById(entry.getKey()).getProductPrice()*entry.getValue()));
                } catch (ItemNotFoundException e) {
                    contentStream.close();
                    throw new RuntimeException(e);
                }
                // contentStream.showText(String.valueOf(items.get(product) * product.getProductPrice()));
                contentStream.endText();

                currentY -= cellHeight;
                i++;
            }

            // Add the total price
            double subtotal = 0;
            try {
                subtotal = cart.value();
            } catch (ItemNotFoundException e) {
                contentStream.close();
                throw new RuntimeException(e);
            }

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(400, 120);
            contentStream.showText("Total         :  " + transaction.getAmount());
            contentStream.newLineAtOffset(0, 15);
            contentStream.showText("Discount  :  " + (subtotal - transaction.getAmount()));
            contentStream.newLineAtOffset(0, 15);
            contentStream.showText("SubTotal  :  " + subtotal);
            contentStream.endText();

            // Close the content stream
            contentStream.close();
            // Sleep to give a sense of delay
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Save the document
            OutputStream outputStream = new FileOutputStream(filename);
            document.save(outputStream);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}