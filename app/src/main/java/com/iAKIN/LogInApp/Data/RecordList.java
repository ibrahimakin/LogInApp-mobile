package com.iAKIN.LogInApp.Data;

import java.util.ArrayList;
import java.util.List;

public class RecordList {
    static private List<Record> myDataset = new ArrayList<Record>();
    static int LastClicked = 0;

    static public List<Record> getList(){
        return myDataset;
    }

    static public void setList(List<Record> records){
        myDataset = records;
    }

    public static int getLastClickedIndex() {
        return LastClicked;
    }

    public static void setLastClickedIndex(int lastClicked) {
        LastClicked = lastClicked;
    }

    public static Record getListElement(int position) {
        LastClicked = position;
        return myDataset.get(position);
    }

    public static Record getLastClickedElement() {
        return myDataset.get(LastClicked);
    }

    public static void deleteElement(Record r) {
        myDataset.remove(r);
    }
}
