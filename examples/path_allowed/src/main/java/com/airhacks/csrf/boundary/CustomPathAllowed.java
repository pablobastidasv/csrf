package com.airhacks.csrf.boundary;

import java.util.regex.Pattern;

import javax.enterprise.inject.Specializes;

import co.pablob.csrf.control.PathAllowedValidator;

@Specializes
public class CustomPathAllowed extends PathAllowedValidator {

    private static final Pattern LOGIN_PATTERN = Pattern.compile("login");

    @Override
    public boolean isPathAllowed(String path) {
        return LOGIN_PATTERN.matcher(path).find();
    }
}
