package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/30.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EvaluateBody implements Serializable{
    public float professionalLevel;
    public float analysis;
    public float expression;

    public float getCompression() {
        return compression;
    }

    public void setCompression(float compression) {
        this.compression = compression;
    }

    public String getAdviceText() {
        return adviceText;
    }

    public void setAdviceText(String adviceText) {
        this.adviceText = adviceText;
    }

    public float getAttitude() {
        return attitude;
    }

    public void setAttitude(float attitude) {
        this.attitude = attitude;
    }

    public float getExpression() {
        return expression;
    }

    public void setExpression(float expression) {
        this.expression = expression;
    }

    public float getAnalysis() {
        return analysis;
    }

    public void setAnalysis(float analysis) {
        this.analysis = analysis;
    }

    public float getProfessionalLevel() {
        return professionalLevel;
    }

    public void setProfessionalLevel(float professionalLevel) {
        this.professionalLevel = professionalLevel;
    }

    public float compression;
    public float attitude;
    public String adviceText;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String companyName;
}
