package com.example.myadou.bean;

import com.tencent.TIMUserProfile;

/**
 * Created by 张晓辉 on 2018/1/2.
 */

public class AdouTimUserProfile {
    int fork;
    int fans;
    int send;

    public TIMUserProfile getProfile() {
        return profile;
    }

    public void setProfile(TIMUserProfile profile) {
        this.profile = profile;
    }

    TIMUserProfile profile;
    public int getSend() {
        return send;
    }

    public void setSend(int send) {
        this.send = send;
    }

    public int getFork() {
        return fork;
    }

    public void setFork(int fork) {
        this.fork = fork;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getReceive() {
        return receive;
    }

    public void setReceive(int receive) {
        this.receive = receive;
    }

    public String getXingzuo() {
        return xingzuo;
    }

    public void setXingzuo(String xingzuo) {
        this.xingzuo = xingzuo;
    }

    int receive;
    int grade;
    String xingzuo;
}
