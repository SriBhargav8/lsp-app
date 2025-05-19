package com.LegalSuvidha.legalsuvidhaproviders.GSTReturnLateFeeCalculator;

public class GstLateFeeRecyclerModelClass {

    private String DueDate;
    private String DueDateA;
    private String DueDateB;
    private String ReturnMonth;
    private int Penalty;
    private String documentID;
    private String dateOfFiling;
    private String nilReturn;

    private GstLateFeeRecyclerModelClass(){

    }

    public GstLateFeeRecyclerModelClass(String dueDate, String dueDateA,String dueDateB, String returnMonth, int penalty, String dateOfFiling, String nilReturn) {
        this.DueDate = dueDate;
        this.DueDateA = dueDateA;
        this.DueDateB= dueDateB;
        this.ReturnMonth = returnMonth;
        this.Penalty = penalty;
        this.dateOfFiling=dateOfFiling;
        this.nilReturn=nilReturn;
    }

    public String getDueDateA() {
        return DueDateA;
    }

    public void setDueDateA(String dueDateA) {
        DueDateA = dueDateA;
    }

    public String getDueDateB() {
        return DueDateB;
    }

    public void setDueDateB(String dueDateB) {
        DueDateB = dueDateB;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getReturnMonth() {
        return ReturnMonth;
    }

    public void setReturnMonth(String returnMonth) {
        ReturnMonth = returnMonth;
    }

    public int getPenalty() {
        return Penalty;
    }

    public void setPenalty(int penalty) {
        Penalty = penalty;
    }

    public String getDocumentID() { return documentID; }

    public void setDocumentID(String documentID) { this.documentID = documentID; }

    public String getDateOfFiling() {
        return dateOfFiling;
    }

    public void setDateOfFiling(String dateOfFiling) {
        this.dateOfFiling = dateOfFiling;
    }

    public String getNilReturn() {
        return nilReturn;
    }

    public void setNilReturn(String nilReturn) {
        this.nilReturn = nilReturn;
    }


}
