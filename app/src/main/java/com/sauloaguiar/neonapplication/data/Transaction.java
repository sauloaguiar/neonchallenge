package com.sauloaguiar.neonapplication.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sauloaguiar on 11/26/16.
 */

public class Transaction implements Comparable<Transaction> {

    @SerializedName("Id")
    private String id;
    @SerializedName("ClienteId")
    private String clientId;
    @SerializedName("Valor")
    private double valor;
    @SerializedName("Data")
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

    @Override
    public int compareTo(Transaction transaction) {
        if(this.getValor() > transaction.getValor()) {
            return -1;
        } else if (this.getValor() < transaction.getValor()) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (Double.compare(that.valor, valor) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null)
            return false;
        return data != null ? data.equals(that.data) : that.data == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        temp = Double.doubleToLongBits(valor);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public void increaseValue(double valor) {
        this.valor += valor;
    }
}
