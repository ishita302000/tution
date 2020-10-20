package com.example.client;

public class UserHelperClass_teacher {
   String teacher_name,  teacher_email,  teacher_phoneNo,  teacher_password ;
   public UserHelperClass_teacher() {
    }
    public UserHelperClass_teacher(String teacher_name, String teacher_email, String teacher_phoneNo, String teacher_password) {
        this.teacher_name = teacher_name;
        this.teacher_email = teacher_email;
        this.teacher_phoneNo = teacher_phoneNo;
        this.teacher_password = teacher_password;
    }
    public String getTeacher_name() {
        return teacher_name;
    }
    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }
    public String getTeacher_email() {
        return teacher_email;
    }
    public void setTeacher_email(String teacher_email) {
        this.teacher_email = teacher_email;
    }
    public String getTeacher_phoneNo() {
        return teacher_phoneNo;
    }
    public void setTeacher_phoneNo(String teacher_phoneNo) {
        this.teacher_phoneNo = teacher_phoneNo;
    }
    public String getTeacher_password() {
        return teacher_password;
    }
    public void setTeacher_password(String teacher_password) {
        this.teacher_password = teacher_password;
    }
}

