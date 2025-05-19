package com.LegalSuvidha.legalsuvidhaproviders.GstVaidation;

import java.util.List;

public class DataFilingList {

    private List<DataFilingList> filingList;

    public DataFilingList(List<DataFilingList> filingList) {
        this.filingList = filingList;
    }

    public DataFilingList() {
    }

    public List<DataFilingList> getFilingList() {
        return filingList;
    }

    public void setFilingList(List<DataFilingList> filingList) {
        this.filingList = filingList;
    }
}
