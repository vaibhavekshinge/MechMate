package com.example.mechmate;

public class Customers {

    String customernames;
    String types;
    String models;
    String vechilenos;
    String prblmdescs;

    public String getCustomerphones() {
        return customerphones;
    }

    public void setCustomerphones(String customerphones) {
        this.customerphones = customerphones;
    }

    String customerphones;

    double customerLatitude, customerLongitude;

    public  Customers(){

    }

    public Customers(String customernames, String types, String models, String vechilenos, String prblmdescs, double customerLatitude, double customerLongitude, String customerphones){

        this.customernames = customernames;
        this.types = types;
        this.models = models;
        this.vechilenos = vechilenos;
        this.prblmdescs = prblmdescs;
        this.customerLatitude = customerLatitude;
        this.customerLongitude = customerLongitude;
        this.customerphones = customerphones;
    }

    public String getCustomernames() {
        return customernames;
    }

    public void setCustomernames(String customernames) {
        this.customernames = customernames;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getVechilenos() {
        return vechilenos;
    }

    public void setVechilenos(String vechilenos) {
        this.vechilenos = vechilenos;
    }

    public String getPrblmdescs() {
        return prblmdescs;
    }

    public void setPrblmdescs(String prblmdescs) {
        this.prblmdescs = prblmdescs;
    }

    public double getCustomerLatitude() { return customerLatitude; }

    public void setCustomerLatitude(double customerLatitude){ this.customerLatitude = customerLatitude;}
    public double getCustomerLongitude() { return customerLongitude; }

    public void setCustomerLongitude(double customerLongitude){ this.customerLongitude = customerLongitude;}
}
