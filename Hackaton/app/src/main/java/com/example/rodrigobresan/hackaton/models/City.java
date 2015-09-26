package com.example.rodrigobresan.hackaton.models;

import java.io.Serializable;

/**
 * Pojo para guardar os valores do JSON.
 * Created by Gabriel on 26/09/2015.
 */
public class City implements Serializable {

    /** Atributos do pojo */
    private String code;
    private String name;
    private String iegm;
    private String ieducation;
    private String ihealth;
    private String igov;
    private String iplan;
    private String ifisc;
    private String ienviroment;
    private String icity;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIegm() {
        return iegm;
    }

    public void setIegm(String iegm) {
        this.iegm = iegm;
    }

    public String getIeducation() {
        return ieducation;
    }

    public void setIeducation(String ieducation) {
        this.ieducation = ieducation;
    }

    public String getIhealth() {
        return ihealth;
    }

    public void setIhealth(String ihealth) {
        this.ihealth = ihealth;
    }

    public String getIgov() {
        return igov;
    }

    public void setIgov(String igov) {
        this.igov = igov;
    }

    public String getIplan() {
        return iplan;
    }

    public void setIplan(String iplan) {
        this.iplan = iplan;
    }

    public String getIfisc() {
        return ifisc;
    }

    public void setIfisc(String ifisc) {
        this.ifisc = ifisc;
    }

    public String getIenviroment() {
        return ienviroment;
    }

    public void setIenviroment(String ienviroment) {
        this.ienviroment = ienviroment;
    }

    public String getIcity() {
        return icity;
    }

    public void setIcity(String icity) {
        this.icity = icity;
    }

    public String toString() {
        return this.name;
    }
}
