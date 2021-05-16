package com.lige110.springreact.ppmtool.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectTaskException extends RuntimeException{

    public ProjectTaskException(String message) {
        super(message);
    }
}
