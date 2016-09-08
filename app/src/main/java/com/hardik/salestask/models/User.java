package com.hardik.salestask.models;

import com.orm.SugarRecord;

/**
 * Created by Hardik Shah on 9/7/2016.
 */
public class User extends SugarRecord {

    String email;
    String password;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
