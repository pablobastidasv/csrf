package com.airhacks.csrf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Specializes;

import co.pablob.csrf.control.MethodAllowedValidator;

@Specializes
public class MethodValidator extends MethodAllowedValidator {
    private List<String> methodsAllowed;

    @PostConstruct
    public void init(){
        methodsAllowed = new ArrayList<>();
        methodsAllowed.add("GET");
        methodsAllowed.add("TRACE");
        methodsAllowed.add("OPTIONS");
        methodsAllowed.add("HEAD");
    }

    @Override
    public boolean isMethodAllowed(String method) {
        return methodsAllowed.contains(method);
    }
}
