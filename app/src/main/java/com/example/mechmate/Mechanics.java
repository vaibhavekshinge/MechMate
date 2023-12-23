package com.example.mechmate;

public class Mechanics {

    String mecnames, shoplocs, adharnos, shopnames, phones, passwordtoregisters, confirmpasswords;

//    double latitude, longitude;

    public Mechanics() {
    }

    public Mechanics(String mecnames, String shoplocs, String adharnos, String shopnames, String phones, String passwordtoregisters, String confirmpasswords) {
        this.mecnames = mecnames;
        this.shoplocs = shoplocs;
        this.adharnos = adharnos;
        this.shopnames = shopnames;
        this.phones = phones;
        this.passwordtoregisters = passwordtoregisters;
        this.confirmpasswords = confirmpasswords;
//        this.latitude = latitude;
//        this.longitude = longitude;
    }

    public String getMecnames() {
        return mecnames;
    }

    public void setMecnames(String mecnames) {
        this.mecnames = mecnames;
    }

    public String getShoplocs() {
        return shoplocs;
    }

    public void setShoplocs(String shoplocs) {
        this.shoplocs = shoplocs;
    }

    public String getAdharnos() {
        return adharnos;
    }

    public void setAdharnos(String adharnos) {
        this.adharnos = adharnos;
    }

    public String getShopnames() {
        return shopnames;
    }

    public void setShopnames(String shopnames) {
        this.shopnames = shopnames;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getPasswordtoregisters() {
        return passwordtoregisters;
    }

    public void setPasswordtoregisters(String passwordtoregisters) {
        this.passwordtoregisters = passwordtoregisters;
    }

    public String getConfirmpasswords() {
        return confirmpasswords;
    }

    public void setConfirmpasswords(String confirmpasswords) {
        this.confirmpasswords = confirmpasswords;
    }

//    public double getLatitude() {return latitude;}
//
//    public void setLatitude(double latitude){
//        this.latitude = latitude;
//    }
//
//    public double getLongitude(){return longitude;}
//
//    public void setLongitude(double longitude){
//        this.longitude = longitude;
//    }
}
