package com.example.client;

public class UserHelperClass_parent {
    String parent_name,  parent_email,  parent_phoneNo,  parent_password ;
    public UserHelperClass_parent() {

    }

    public UserHelperClass_parent(String parent_name, String parent_email, String parent_phoneNo, String parent_password) {
        this.parent_name = parent_name;
        this.parent_email = parent_email;
        this.parent_phoneNo = parent_phoneNo;
        this.parent_password = parent_password;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getParent_email() {
        return parent_email;
    }

    public void setParent_email(String parent_email) {
        this.parent_email = parent_email;
    }

    public String getParent_phoneNo() {
        return parent_phoneNo;
    }

    public void setParent_phoneNo(String parent_phoneNo) {
        this.parent_phoneNo = parent_phoneNo;
    }

    public String getParent_password() {
        return parent_password;
    }

    public void setParent_password(String parent_password) {
        this.parent_password = parent_password;
    }
}
