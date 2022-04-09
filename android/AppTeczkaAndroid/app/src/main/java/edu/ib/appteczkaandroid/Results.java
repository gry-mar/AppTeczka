package edu.ib.appteczkaandroid;

import java.util.ArrayList;

public class Results {
    protected String nlmDisclaimer;
    protected ArrayList<ArrayList> fullInteractionTypeGroup;

    public Results(String nlmDisclaimer, ArrayList<ArrayList> fullInteractionTypeGroup) {
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

    public ArrayList<ArrayList> getFullInteractionTypeGroup() {
        return fullInteractionTypeGroup;
    }

    public void setFullInteractionTypeGroup(ArrayList<ArrayList> fullInteractionTypeGroup) {
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
