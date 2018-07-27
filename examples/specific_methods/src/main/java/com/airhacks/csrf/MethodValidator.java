package com.airhacks.csrf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Specializes;

import co.pablob.csrf.control.MethodAllowedValidator;

@Specializes
public class MethodValidator extends MethodAllowedValidator {
    private List<String> methodsAllowed;

    @PostConstruct
    public void init(){
        List<String> methods = new ArrayList<>();
        methods.add("GET");
        methods.add("TRACE");
        methods.add("OPTIONS");
        methods.add("HEAD");

        this.methodsAllowed = Collections.synchronizedList(methods);
    }

    @Override
    public boolean isMethodAllowed(String method) {
        return methodsAllowed.contains(method);
    }
}
