package com.LegalSuvidha.legalsuvidhaproviders.GstVaidation;

public class ReturnTypeModel {

    private String monthSession;
    private Boolean GSTR1;
    private Boolean GSTR3B;
    private String GSTR1date,GSTR3Bdate;

    public ReturnTypeModel(String monthSession, Boolean GSTR1, Boolean GSTR3B, String GSTR1date, String GSTR3Bdate) {
        this.monthSession = monthSession;
        this.GSTR1 = GSTR1;
        this.GSTR3B = GSTR3B;
        this.GSTR1date = GSTR1date;
        this.GSTR3Bdate = GSTR3Bdate;
    }


    public String getMonthSession() {
        return monthSession;
    }

    public void setMonthSession(String monthSession) {
        this.monthSession = monthSession;
    }



    public String getGSTR1date() {
        return GSTR1date;
    }

    public void setGSTR1date(String GSTR1date) {
        this.GSTR1date = GSTR1date;
    }

    public String getGSTR3Bdate() {
        return GSTR3Bdate;
    }

    public void setGSTR3Bdate(String GSTR3Bdate) {
        this.GSTR3Bdate = GSTR3Bdate;
    }

    public Boolean getGSTR1() {
        return GSTR1;
    }

    public void setGSTR1(Boolean GSTR1) {
        this.GSTR1 = GSTR1;
    }

    public Boolean getGSTR3B() {
        return GSTR3B;
    }

    public void setGSTR3B(Boolean GSTR3B) {
        this.GSTR3B = GSTR3B;
    }
}
