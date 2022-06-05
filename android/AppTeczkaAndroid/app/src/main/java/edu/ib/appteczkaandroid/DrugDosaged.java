package edu.ib.appteczkaandroid;

public class DrugDosaged {
    private String name;
    private String endDate;
    private String dosagesPerDay;
    private String id;

    public DrugDosaged(String name, String endDate, String dosagesPerDay) {
        this.name = name;
        this.endDate = endDate;
        this.dosagesPerDay = dosagesPerDay;
    }

    public DrugDosaged(String name, String endDate, String dosagesPerDay, String id) {
        this.name = name;
        this.endDate = endDate;
        this.dosagesPerDay = dosagesPerDay;
        this.id = id;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDosagesPerDay() {
        return dosagesPerDay;
    }

    public void setDosagesPerDay(String dosagesPerDay) {
        this.dosagesPerDay = dosagesPerDay;
    }

    @Override
    public String toString() {
        return "DrugDosaged{" +
                "name='" + name + '\'' +
                ", endDate='" + endDate + '\'' +
                ", dosagesPerDay='" + dosagesPerDay + '\'' +
                '}';
    }
}
