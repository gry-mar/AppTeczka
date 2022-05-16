package edu.ib.appteczkaandroid;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FullInteractionType implements Serializable {
    @SerializedName("description")
    protected String description;

    public FullInteractionType() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
