package com.portfolio.recipebook.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String name, Long id){
        super("No " + name + " with id: " + id);
    }

}
