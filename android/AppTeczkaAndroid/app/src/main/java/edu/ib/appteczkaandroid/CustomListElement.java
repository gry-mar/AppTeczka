package edu.ib.appteczkaandroid;

public class CustomListElement {
    private String name;
    private String time;
    private boolean checked;
    private int id;

    public CustomListElement(String name, String time, boolean checked) {
        this.name = name;
        this.time = time;
        this.checked = checked;
    }

    public CustomListElement(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public CustomListElement(String name, String time, boolean checked, int id) {
        this.name = name;
        this.time = time;
        this.checked = checked;
        this.id = id;
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

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return "CustomListElement{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", isChecked=" + checked +
                '}';
    }

}
