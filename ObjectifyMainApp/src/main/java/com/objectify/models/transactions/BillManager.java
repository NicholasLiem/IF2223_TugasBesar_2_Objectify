package com.objectify.models.transactions;

import com.objectify.models.entities.User;
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

    public Bill getBillByUser(User user) {
        for (Bill bill : bills) {
            if (bill.getUser().equals(user)) {
                return bill;
            }
        }
        return null;
    }

    public void updateBill(Bill bill) {
        Bill tempBill = getBillByUser(bill.getUser());
        if (tempBill != null) {
            this.removeBill(tempBill);
        }
        this.addBill(bill);
    }

    public void addBill(Bill bill){
        this.bills.add(bill);
    }

    public void removeBill(Bill bill){
        this.bills.remove(bill);
    }
    
}
