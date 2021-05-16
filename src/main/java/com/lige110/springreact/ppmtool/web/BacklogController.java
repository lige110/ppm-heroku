package com.lige110.springreact.ppmtool.web;


import com.lige110.springreact.ppmtool.domain.Project;
import com.lige110.springreact.ppmtool.domain.ProjectTask;
import com.lige110.springreact.ppmtool.services.MapValidationErrorService;
import com.lige110.springreact.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;



    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask,
                                            BindingResult result, @PathVariable String backlog_id, Principal principal){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;


        ProjectTask projectTask1= projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());

        return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<?> getProjectBacklog(@PathVariable String backlog_id, Principal principal){


//        return projectTaskService.findAllProjectTasks();

        return projectTaskService.findBacklogById(backlog_id, principal.getName()); // if the backlog not exist, will be handled later

    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,Principal principal){

        ProjectTask projectTask = projectTaskService.findProjectTaskByPTSequence(backlog_id,pt_id,principal.getName());
        return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
    }


    @PatchMapping("/{backlog_id}/{pt_id}")
    public  ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                                @PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        ProjectTask updatedTask =  projectTaskService.updateByProjectTaskSequence(projectTask,backlog_id,pt_id,principal.getName());

        return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
        backlog_id = backlog_id.toUpperCase();
        pt_id = pt_id.toUpperCase();

        projectTaskService.deleteByProjectSequence(backlog_id,pt_id,principal.getName());


        return new ResponseEntity<>(
                "Project Task in project '"+backlog_id+"' with sequence '"+pt_id+"' is deleted",HttpStatus.OK);


    }
}
