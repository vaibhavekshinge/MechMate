package com.example.mechmate;

public class Connections {

    private String mechanicId;
    private String customerId;

    String CusName, CusPhoneNo, CusPrblm, CusModel;

    public Connections() {
        // Default constructor required for Firebase
    }

    public Connections(String mechanicId, String customerId) {
        this.mechanicId = mechanicId;
        this.customerId = customerId;
    }

    public String getMechanicId() {
        return mechanicId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
