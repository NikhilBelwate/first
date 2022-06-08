package com.demo.model;

import io.micronaut.context.annotation.Bean;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Bean
public class Condition {
    private String param;
    private String operator;
    private Object value;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
