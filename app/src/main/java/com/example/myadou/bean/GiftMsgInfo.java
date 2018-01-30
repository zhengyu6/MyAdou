package com.example.myadou.bean;

/**
 * Created by 张晓辉 on 2018/1/30.
 */

public class GiftMsgInfo {

    String avatar;
    String adouID;
    Gift gift;
    int giftNum;

    public GiftMsgInfo(String avatar, String adouID, Gift gift, int giftNum) {
        this.avatar = avatar;
        this.adouID = adouID;
        this.gift = gift;
        this.giftNum = giftNum;
    }

    public GiftMsgInfo() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAdouID() {
        return adouID;
    }

    public void setAdouID(String adouID) {
        this.adouID = adouID;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }
}
