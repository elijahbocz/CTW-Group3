package com.ctw_group3.foodsaver;

public class SubmissionsModel {
    String address,foodDesc,foodImageLink, foodName, mobile, ownerName, storename, docId;
    boolean claimed;

    public SubmissionsModel(){}

    public SubmissionsModel(String address, boolean claimed, String foodDesc, String foodImageLink, String foodName, String mobile, String ownerName, String storename){
        this.address = address;
        this.claimed = claimed;
        this.foodDesc = foodDesc;
        this.foodImageLink = foodImageLink;
        this.foodName = foodName;
        this.mobile =mobile;
        this.ownerName = ownerName;
        this.storename =storename;
        this.docId = docId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getClaimed() { return claimed; }

    public void setClaimed(boolean claimed) { this.claimed = claimed; }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public String getFoodImageLink() {
        return foodImageLink;
    }

    public void setFoodImageLink(String foodImageLink) {
        this.foodImageLink = foodImageLink;
    }

    public String getfoodName() {
        return foodName;
    }

    public void setfoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getDocId() { return docId; }

    public void setDocId(String docId) { this.docId = docId; }
}