package com.caa.kata.utils.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorServiceTest {
    @Test
    void validateObject_nullObject_throwsIllegalArgumentException() {
        String name = "TestObject";
        assertThrows(IllegalArgumentException.class, () -> ValidatorService.validateObject(null, name));
    }

    @Test
    void validateObject_nonNullObject_doesNotThrowException() {
        String name = "TestObject";
        ValidatorService.validateObject(new Object(), name);
    }

    @Test
    void validateList_nullList_throwsIllegalArgumentException() {
        String name = "TestList";
        assertThrows(IllegalArgumentException.class, () -> ValidatorService.validateList(null, name));
    }

    @Test
    void validateList_emptyList_throwsIllegalArgumentException() {
        String name = "TestList";
        assertThrows(IllegalArgumentException.class, () -> ValidatorService.validateList(List.of(), name));
    }

    @Test
    void validateList_nonEmptyList_doesNotThrowException() {
        String name = "TestList";
        List<String> list = List.of("item1", "item2");
        ValidatorService.validateList(list, name);
    }
}