package com.example.mechmate;

public class Mechanics {

    String mecnames, mecemail, adharnos, shopnames, phones, passwordtoregisters, confirmpasswords;

    double meclatitude, meclongitude;

    public Mechanics() {
    }

    public Mechanics(String mecnames, String mecemail, String adharnos, String shopnames, String phones, String passwordtoregisters, String confirmpasswords, double meclatitude, double meclongitude) {
        this.mecnames = mecnames;
        this.mecemail = mecemail;
        this.adharnos = adharnos;
        this.shopnames = shopnames;
        this.phones = phones;
        this.passwordtoregisters = passwordtoregisters;
        this.confirmpasswords = confirmpasswords;
        this.meclatitude = meclatitude;
        this.meclongitude = meclongitude;
    }

    public String getMecnames() {
        return mecnames;
    }

    public void setMecnames(String mecnames) {
        this.mecnames = mecnames;
    }

    public String getMecemail() {
        return mecemail;
    }

    public void setMecemail(String shoplocs) {
        this.mecemail = mecemail;
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
    public double getMeclatitude() {
        return meclatitude;
    }

    public void setMeclatitude(double meclatitude) {
        this.meclatitude = meclatitude;
    }

    public double getMeclongitude() {
        return meclongitude;
    }

    public void setMeclongitude(double meclongitude) {
        this.meclongitude = meclongitude;
    }

}
