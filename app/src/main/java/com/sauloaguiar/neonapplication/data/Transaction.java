package com.sauloaguiar.neonapplication.data;

/**
 * Created by sauloaguiar on 11/26/16.
 */

public class Transaction {

    private String id;
    private String clientId;
    private double valor;
    private String data;

    public Transaction(String id, String clientId, double value, String date) {
        this.id = id;
        this.clientId = clientId;
        this.valor = value;
        this.data = date;
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public double getValor() {
        return valor;
    }

    public String getData() {
        return data;
    }

}
