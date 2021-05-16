package com.lige110.springreact.ppmtool.exceptions;


public class ProjectTaskExceptionResponse {
    private String ProjectTaskException;

    public ProjectTaskExceptionResponse(String projectTaskException) {
        ProjectTaskException = projectTaskException;
    }

    public String getProjectTaskException() {
        return ProjectTaskException;
    }

    public void setProjectTaskException(String projectTaskException) {
        ProjectTaskException = projectTaskException;
    }
}
