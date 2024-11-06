package com.tdcollab.spoterly.core.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message){
        super(message);
    }
}
