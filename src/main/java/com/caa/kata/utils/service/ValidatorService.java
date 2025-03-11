package com.caa.kata.utils.service;

import com.caa.kata.utils.constant.Constants;

import java.util.List;

public final class ValidatorService {


    public static void validateObject(Object object, String name) {
        if (object == null) {
            throw new IllegalArgumentException(name.concat(Constants.NULL_OBJECT));
        }
    }

    public static void validateList(List<?> list, String name) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(name.concat(Constants.NULL_OBJECT));
        }
    }


}
