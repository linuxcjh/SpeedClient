package com.rongfeng.speedclient.datanalysis;

public class Person {


    public String client_name;
    public String client_id;

    public String contact_name;
    public String contact_phone;
    public String contact_id;


    public Person() {
    }


    public Person(String client_id, String client_name, String contact_id, String contact_name, String contact_phone) {
        this.client_name = client_name;
        this.client_id = client_id;
        this.contact_name = contact_name;
        this.contact_phone = contact_phone;
        this.contact_id = contact_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }
}
