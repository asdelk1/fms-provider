package com.owerp.fmsprovider.system.advice;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException{
    private final String message;

    public EntityNotFoundException(String entity, Long id) {
       this.message = "No record found with id " + Long.toString(id) + " in Entity " + entity;
    }
}
