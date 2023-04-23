package com.objectify.models.transactions;

import java.util.ArrayList;

public class BillManager {

    private ArrayList<Bill> bills;

    public BillManager() {
        this.bills = new ArrayList<>();
    }

    public ArrayList<Bill> getBills() {
        return bills;
    }

    public void setBills(ArrayList<Bill> bills) {
        this.bills = bills;
    }

    public void addBill(Bill bill){
        this.bills.add(bill);
    }

    public void removeBill(Bill bill){
        this.bills.remove(bill);
    }
    
}
