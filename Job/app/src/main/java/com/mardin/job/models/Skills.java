package com.mardin.job.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/3.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Skills implements Serializable{
    public List<TalentList> advices;
    public float attitude;

    public float getCompression() {
        return compression;
    }

    public void setCompression(float compression) {
        this.compression = compression;
    }

    public float getProfessionalLevel() {
        return professionalLevel;
    }

    public void setProfessionalLevel(float professionalLevel) {
        this.professionalLevel = professionalLevel;
    }

    public float getAnalysis() {
        return analysis;
    }

    public void setAnalysis(float analysis) {
        this.analysis = analysis;
    }

    public float getExpression() {
        return expression;
    }

    public void setExpression(float expression) {
        this.expression = expression;
    }

    public float getAttitude() {
        return attitude;
    }

    public void setAttitude(float attitude) {
        this.attitude = attitude;
    }

    public float compression;
    public float expression;
    public float analysis;
    public float professionalLevel;
}
