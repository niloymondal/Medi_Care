package com.medi.medicare;

public class PatientClass {

    String  PatientSignUpFirstName,PatientSignUpLastName,PatientSignUpEmail,
    PatientSignUpPassword ,PatientSignUpAge,PrevDisease,gender,pin,calling,pick,imageStr;

    public PatientClass() {
    }

    public PatientClass(String patientSignUpFirstName, String patientSignUpLastName, String patientSignUpEmail,
                        String patientSignUpPassword, String patientSignUpAge,
                        String prevDisease, String gender, String pin,String calling,String pick,String imageStr) {
        PatientSignUpFirstName = patientSignUpFirstName;
        PatientSignUpLastName = patientSignUpLastName;
        PatientSignUpEmail = patientSignUpEmail;
        PatientSignUpPassword = patientSignUpPassword;
        PatientSignUpAge = patientSignUpAge;
        PrevDisease = prevDisease;
        this.gender = gender;
        this.pin = pin;
        this.calling=calling;
        this.pick=pick;
        this.imageStr=imageStr;
    }

    public String getPatientSignUpFirstName() {
        return PatientSignUpFirstName;
    }

    public void setPatientSignUpFirstName(String patientSignUpFirstName) {
        PatientSignUpFirstName = patientSignUpFirstName;
    }

    public String getPatientSignUpLastName() {
        return PatientSignUpLastName;
    }

    public void setPatientSignUpLastName(String patientSignUpLastName) {
        PatientSignUpLastName = patientSignUpLastName;
    }

    public String getPatientSignUpEmail() {
        return PatientSignUpEmail;
    }

    public void setPatientSignUpEmail(String patientSignUpEmail) {
        PatientSignUpEmail = patientSignUpEmail;
    }

    public String getPatientSignUpPassword() {
        return PatientSignUpPassword;
    }

    public void setPatientSignUpPassword(String patientSignUpPassword) {
        PatientSignUpPassword = patientSignUpPassword;
    }

    public String getPatientSignUpAge() {
        return PatientSignUpAge;
    }

    public void setPatientSignUpAge(String patientSignUpAge) {
        PatientSignUpAge = patientSignUpAge;
    }

    public String getPrevDisease() {
        return PrevDisease;
    }

    public void setPrevDisease(String prevDisease) {
        PrevDisease = prevDisease;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCalling() {
        return calling;
    }

    public void setCalling(String calling) {
        this.calling = calling;
    }

    public String getPick() {
        return pick;
    }

    public void setPick(String pick) {
        this.pick = pick;
    }

    public String getImageStr() {
        return imageStr;
    }

    public void setImageStr(String imageStr) {
        this.imageStr = imageStr;
    }
}
