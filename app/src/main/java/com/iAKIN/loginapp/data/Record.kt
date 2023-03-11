package com.iAKIN.loginapp.data

class Record {
    var id: Int = 0;
    var site: String = "";
    var email: String = "";
    var username: String = "";
    var hint: String = "";
    var tags: String = "";

    // not show
    var changingDate: String = "";
    var registrationDate: String = "";
    var hash: String = "";
    var sync: Int = 2;


    constructor(site: String, email: String, username: String, hint: String, tags: String) {
        this.site = site
        this.email = email
        this.username = username
        this.hint = hint
        this.tags = tags
    }

    constructor(
        id: Int,
        site: String,
        email: String,
        username: String,
        hint: String,
        tags: String,
        changingDate: String,
        registrationDate: String,
        hash: String,
        sync: Int
    ) {
        this.id = id
        this.site = site
        this.email = email
        this.username = username
        this.hint = hint
        this.tags = tags
        this.changingDate = changingDate
        this.registrationDate = registrationDate
        this.hash = hash
        this.sync = sync
    }

    constructor() {}
}