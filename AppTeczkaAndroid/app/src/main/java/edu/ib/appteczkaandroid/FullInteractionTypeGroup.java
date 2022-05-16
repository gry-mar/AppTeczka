package edu.ib.appteczkaandroid;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FullInteractionTypeGroup implements Serializable {
    @SerializedName("sourceDisclaimer")
    protected String sourceDisclaimer;
    @SerializedName("sourceName")
    protected String sourceName;
    @SerializedName("fullInteractionType")
    protected List<FullInteractionType> fullInteractionTypeList;

    public FullInteractionTypeGroup(String sourceDisclaimer, String sourceName, List<FullInteractionType> fullInteractionTypeList) {
        this.sourceDisclaimer = sourceDisclaimer;
        this.sourceName = sourceName;
        this.fullInteractionTypeList = fullInteractionTypeList;
    }

    public FullInteractionTypeGroup() {
    }

    public String getSourceDisclaimer() {
        return sourceDisclaimer;
    }

    public void setSourceDisclaimer(String sourceDisclaimer) {
        this.sourceDisclaimer = sourceDisclaimer;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public List<FullInteractionType> getFullInteractionTypeList() {
        return fullInteractionTypeList;
    }

    public void setFullInteractionTypeList(List<FullInteractionType> fullInteractionTypeList) {
        this.fullInteractionTypeList = fullInteractionTypeList;
    }
}
