package com.rongfeng.speedclient.datanalysis;

public class ClientModel {


    public String client_name;
    public String client_id;
    public String client_info;

    public String contact_name;
    public String contact_phone;
    public String contact_id;


    public ClientModel() {

    }


    public ClientModel(String client_id, String client_name, String client_info,String contact_id, String contact_name, String contact_phone) {
        this.client_name = client_name;
        this.client_id = client_id;
        this.contact_name = contact_name;
        this.contact_phone = contact_phone;
        this.contact_id = contact_id;
        this.client_info = client_info;
    }

    public ClientModel(String client_id, String client_name, String client_info) {
        this.client_name = client_name;
        this.client_id = client_id;
        this.client_info = client_info;
    }

    public String getClient_info() {
        return client_info;
    }

    public void setClient_info(String client_info) {
        this.client_info = client_info;
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
