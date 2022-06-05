package edu.ib.appteczkaandroid;

public class DrugInAll {
    private String name;
    private String date;


    public DrugInAll(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public DrugInAll() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DrugInAll{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                //", id='" + id + '\'' +
                '}';
    }
}
