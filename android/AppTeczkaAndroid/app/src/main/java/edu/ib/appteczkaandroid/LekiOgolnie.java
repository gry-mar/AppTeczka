package edu.ib.appteczkaandroid;

public class LekiOgolnie {

    public String nazwa;
    public String data;

    public LekiOgolnie(String nazwa, String data) {
        this.nazwa = nazwa;
        this.data = data;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
