package com.sellanddonate.app.firebase;


//data model to upload the user coustom field to firebase database
public class User {
    public String
            userName,
            email,
            phoneNumber,
            address,
            member_since;

    public User() {
    }

    public User(String userName, String email, String phoneNumber, String address, String member_since) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.member_since = member_since;
    }

}
