package com.objectify.datastore.testing;

import java.util.ArrayList;

public class Orang {
    private String name;
    private String noPenduduk;
    private int socialCredit;
    private ArrayList<Orang> daftarKerabat;

    public Orang() {}

    public Orang(String name, String noPenduduk, int socialCredit, ArrayList<Orang> daftarKerabat) {
        this.name = name;
        this.noPenduduk = noPenduduk;
        this.socialCredit = socialCredit;
        this.daftarKerabat = daftarKerabat;
    }

    @Override
    public String toString() {
        return "Orang{" +
                "name='" + name + '\'' +
                ", noPenduduk='" + noPenduduk + '\'' +
                ", socialCredit=" + socialCredit +
                ", daftarKerabat=" + daftarKerabat +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoPenduduk() {
        return noPenduduk;
    }

    public void setNoPenduduk(String noPenduduk) {
        this.noPenduduk = noPenduduk;
    }

    public int getSocialCredit() {
        return socialCredit;
    }

    public void setSocialCredit(int socialCredit) {
        this.socialCredit = socialCredit;
    }

    public ArrayList<Orang> getDaftarKerabat() {
        return daftarKerabat;
    }

    public void setDaftarKerabat(ArrayList<Orang> daftarKerabat) {
        this.daftarKerabat = daftarKerabat;
    }

    public void addKerabat(Orang kerabat){
        this.daftarKerabat.add(kerabat);
    }
}
