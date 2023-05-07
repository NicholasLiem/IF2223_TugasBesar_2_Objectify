package com.objectify.models.transactions;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

public class TransactionPDFGenerator extends Thread {

    private Transaction transaction;

    public TransactionPDFGenerator(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void run() {
        try {
            String filename = "transaction_" + transaction.getId() + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            document.add(new Paragraph("Transaction ID: " + transaction.getId()));
            document.add(new Paragraph("Date Time: " + transaction.getDateTime()));
            document.add(new Paragraph("Cart Items:\n" + transaction.getCart().toString()));
            document.add(new Paragraph("Discount: " + (transaction.getCart().value() - transaction.getAmount())));
            document.add(new Paragraph("Amount: $" + transaction.getAmount()));
            document.close();
            System.out.println("PDF generated successfully!");
        } catch (Exception e) {
            System.err.println("Error generating PDF: " + e.getMessage());
        }
    }
}
