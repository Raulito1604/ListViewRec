package com.dam.eva.listviewrec;

public class Bloc {
    private String data;
    private String termperatura;
    private String fredCalor;


    public Bloc(String data, String termperatura, String fredCalor) {
        this.data = data;
        this.termperatura = termperatura;
        this.fredCalor = fredCalor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTermperatura() {
        return termperatura;
    }

    public void setTermperatura(String termperatura) {
        this.termperatura = termperatura;
    }

    public String getFredCalor() {
        return fredCalor;
    }

    public void setFredCalor(String fredCalor) {
        this.fredCalor = fredCalor;
    }
}
