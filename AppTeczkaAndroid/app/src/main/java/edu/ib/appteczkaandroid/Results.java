package edu.ib.appteczkaandroid;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Results implements Serializable {
    @SerializedName("nlmDisclaimer")
    protected String nlmDisclaimer;
    @SerializedName("fullInteractionTypeGroup")
    protected ArrayList<FullInteractionTypeGroup> fullInteractionTypeGroup;

    public Results(String nlmDisclaimer, ArrayList<FullInteractionTypeGroup> fullInteractionTypeGroup) {
        this.nlmDisclaimer = nlmDisclaimer;
        this.fullInteractionTypeGroup = fullInteractionTypeGroup;
    }

    public Results() {
    }

    public String getNlmDisclaimer() {
        return nlmDisclaimer;
    }

    public void setNlmDisclaimer(String nlmDisclaimer) {
        this.nlmDisclaimer = nlmDisclaimer;
    }

    public ArrayList<FullInteractionTypeGroup> getFullInteractionTypeGroup() {
        return fullInteractionTypeGroup;
    }

    public void setFullInteractionTypeGroup(ArrayList<FullInteractionTypeGroup> fullInteractionTypeGroup) {
        this.fullInteractionTypeGroup = fullInteractionTypeGroup;
    }

    @Override
    public String toString() {
        return "Results{" +
                "nlmDisclaimer='" + nlmDisclaimer + '\'' +
                ", fullInteractionTypeGroup=" + fullInteractionTypeGroup +
                '}';
    }
}
