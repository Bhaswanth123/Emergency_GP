package com.example.aclass.emergency;

public class EmerData {

    String id;
    String name;
    String phone;
    String fphone;

    public EmerData()
    {

    }
    public EmerData(String id,String name,String phone,String fphone)
    {
        this.id=id;
        this.name=name;
        this.phone=phone;
        this.fphone=fphone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getFphone() {
        return fphone;
    }
}
