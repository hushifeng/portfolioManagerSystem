package com.team5.Pojo;

/**
 * Created by ellen on 2018/8/12.
 */
public class FundManager {

    private String soeid;
    private String name;

    public FundManager() {
    }

    public FundManager(String soeid, String name) {
        this.soeid = soeid;
        this.name = name;
    }

    public String getSoeid() {
        return soeid;
    }

    public void setSoeid(String soeid) {
        this.soeid = soeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "" + this.soeid + "" + this.name;
    }
}
