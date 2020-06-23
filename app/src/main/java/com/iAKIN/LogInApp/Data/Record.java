package com.iAKIN.LogInApp.Data;

public class Record {

    private int Id, Sync;
    private String Site, EMail, Username, Hint, Labels, RegistrationDate, ChangingDate, Hash;

    public Record(int id, String site, String email, String username, String hint, String labels, String registrationDate, String changingDate, int sync, String hash)
    {
        Id = id;
        Site = site;
        EMail = email;
        Username = username;
        Hint = hint;
        Labels = labels;
        RegistrationDate = registrationDate;
        ChangingDate = changingDate;
        Sync = sync;
        Hash = hash;
    }

    public Record(String site, String email)
    {
        Site = site;
        EMail = email;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getSync() {
        return Sync;
    }

    public void setSync(int sync) {
        Sync = sync;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getHint() {
        return Hint;
    }

    public void setHint(String hint) {
        Hint = hint;
    }

    public String getLabels() {
        return Labels;
    }

    public void setLabels(String labels) {
        Labels = labels;
    }

    public String getRegistrationDate() {
        return RegistrationDate;
    }

    public void setRegistrationDate(String registrationDate) { RegistrationDate = registrationDate; }

    public String getChangingDate() {
        return ChangingDate;
    }

    public void setChangingDate(String changingDate) {
        ChangingDate = changingDate;
    }

    public String getHash() {
        return Hash;
    }

    public void setHash(String hash) {
        Hash = hash;
    }
}
