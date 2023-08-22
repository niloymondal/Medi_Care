package com.medi.medicare;

public class prescriptionClass {
    String id,prescription,docid;

    public prescriptionClass() {
    }

    public prescriptionClass(String id,String docid, String prescription) {
        this.id = id;
        this.prescription = prescription;
        this.docid = docid;
    }

    public String getId() {
        return id;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
}
