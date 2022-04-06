package edu.ib.appteczkaandroid;

public class PlannerObject {
    String drugName;
    String time;
    boolean isChecked;

    public PlannerObject(String drugName, String time, boolean isChecked) {
        this.drugName = drugName;
        this.time = time;
        this.isChecked = isChecked;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
