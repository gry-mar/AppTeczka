package edu.ib.appteczkaandroid;

public class DrugInAll {
    private String name;
    private String date;
    //private String id;

    public DrugInAll(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public DrugInAll() {
    }

    //    public DrugInAll(String name, String date, String id) {
//        this.name = name;
//        this.date = date;
//        this.id = id;
//    }

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

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    @Override
    public String toString() {
        return "DrugInAll{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                //", id='" + id + '\'' +
                '}';
    }
}
