package edu.ib.appteczkaandroid;

public class CustomListElement {
    private String name;
    private String time;
    private boolean checked;

    public CustomListElement(String name, String time, boolean checked) {
        this.name = name;
        this.time = time;
        this.checked = checked;
    }

    public CustomListElement(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "CustomListElement{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", isChecked=" + checked +
                '}';
    }

}
