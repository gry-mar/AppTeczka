package edu.ib.appteczkaandroid;

import java.util.ArrayList;

public interface IOGetAllInteractions {
    void onAllInteractions(ArrayList<String> response, ArrayList<String> allDrugs, boolean onSuccess);
}
