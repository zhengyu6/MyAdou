package com.example.myadou.qiniu;

/**
 * Created by 张晓辉 on 2018/1/9.
 */

public class TokenBean {
    String scope;
    long deadline;
    ReturnBody returnBody;

    public TokenBean(String scope, long deadline, ReturnBody returnBody) {
        this.scope = scope;
        this.deadline = deadline;
        this.returnBody = returnBody;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public TokenBean(String scope, long deadline) {
        this.scope = scope;
        this.deadline = deadline;
    }
    static class ReturnBody{
        String name;
        String size;
        String w;
        String h;
        String hash;

        public ReturnBody(String name, String size, String w, String h, String hash) {
            this.name = name;
            this.size = size;
            this.w = w;
            this.h = h;
            this.hash = hash;
        }
    }
}
