package edu.ib.appteczkaandroid;

public class DrugDosaged {
    private String name;
    private String endDate;
    private String dosagesPerDay;

    public DrugDosaged(String name, String endDate, String dosagesPerDay) {
        this.name = name;
        this.endDate = endDate;
        this.dosagesPerDay = dosagesPerDay;
    }

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
