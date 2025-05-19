package com.LegalSuvidha.legalsuvidhaproviders.GstVaidation;

public class FillingData {


    private String fy;
    private String taxp;
    private String mof;
    private String dof;
    private String rtntype;
    private String arn;
    private String status;

    public FillingData() {
    }

    public FillingData(String fy, String taxp, String mof, String dof, String rtntype, String arn, String status) {
        this.fy = fy;
        this.taxp = taxp;
        this.mof = mof;
        this.dof = dof;
        this.rtntype = rtntype;
        this.arn = arn;
        this.status = status;
    }


    public String getFy() {
        return fy;
    }

    public void setFy(String fy) {
        this.fy = fy;
    }

    public String getTaxp() {
        return taxp;
    }

    public void setTaxp(String taxp) {
        this.taxp = taxp;
    }

    public String getMof() {
        return mof;
    }

    public void setMof(String mof) {
        this.mof = mof;
    }

    public String getDof() {
        return dof;
    }

    public void setDof(String dof) {
        this.dof = dof;
    }

    public String getRtntype() {
        return rtntype;
    }

    public void setRtntype(String rtntype) {
        this.rtntype = rtntype;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
