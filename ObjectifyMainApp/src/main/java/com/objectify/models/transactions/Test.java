package com.objectify.models.transactions;

import com.objectify.models.items.ShoppingCart;
import com.objectify.models.items.Product;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) {
        // Create some sample products
        Product product1 = new Product(10,"apple",12,10,null,"");
        Product product2 = new Product(10,"banana",21,10,null,"");
        Product product3 = new Product(10,"orange",10,10,null,"");

        // Create a cart and add some items to it
        ShoppingCart cart = new ShoppingCart();
        cart.addCartItem(product1,3);
        cart.addCartItem(product2, 2);
        cart.addCartItem(product3, 1);

        // Create a transaction from the cart
        double amount = cart.value();
        // Transaction transaction = new Transaction(1,LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), amount, cart);

        TransactionHistory transactions = new TransactionHistory();
        for (int i = 0; i < 10; i++) {
            String desc = "desc" + (i+1);
            TransactionPDFGenerator fixedBill = new TransactionPDFGenerator(new Transaction(i+1,LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), desc, amount, cart));
            fixedBill.start();
            transactions.add(new Transaction(i+1,LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), desc, amount, cart));
        }

        // Generate a PDF bill for the transaction
        TransactionHistoryPDFGenerator pdfGenerator = new TransactionHistoryPDFGenerator(transactions);
        pdfGenerator.start(); // This will run the PDF generation in a separate thread
    }
}
