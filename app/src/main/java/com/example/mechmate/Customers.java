package com.example.mechmate;

public class Customers {

    String customernames, types, models, vechilenos, prblmdescs;

    public  Customers(){

    }

    public Customers(String customernames, String types, String models, String vechilenos, String prblmdescs){

        this.customernames = customernames;
        this.types = types;
        this.models = models;
        this.vechilenos = vechilenos;
        this.prblmdescs = prblmdescs;
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
}
