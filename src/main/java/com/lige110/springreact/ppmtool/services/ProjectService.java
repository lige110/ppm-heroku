package com.lige110.springreact.ppmtool.services;

import com.lige110.springreact.ppmtool.domain.Backlog;
import com.lige110.springreact.ppmtool.domain.Project;
import com.lige110.springreact.ppmtool.domain.User;
import com.lige110.springreact.ppmtool.exceptions.ProjectIdException;
import com.lige110.springreact.ppmtool.exceptions.ProjectNotFoundException;
import com.lige110.springreact.ppmtool.repositories.BacklogRepository;
import com.lige110.springreact.ppmtool.repositories.ProjectRepository;
import com.lige110.springreact.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ProjectService {


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username){

         if(project.getId() != null){
             Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

             if(existingProject != null && (!existingProject.getProjectLeader().equals(username))){
                 throw new ProjectNotFoundException("Project not found in your account");
             }else if(existingProject == null){
                 throw new ProjectNotFoundException("Project with ID: "+project.getId()+"does not exist");
             }
         }


        try {

            User user = userRepository.findByUsername(username);

            project.setUser(user);
            project.setProjectLeader(user.getUsername());

            String identifier = project.getProjectIdentifier().toUpperCase();
            project.setProjectIdentifier(identifier);


            if(project.getId() == null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(identifier);

            }else {
                project.setBacklog(backlogRepository.findByProjectIdentifier(identifier));
            }

            return projectRepository.save(project); // judge if the ID already exists here

        }catch (Exception e){
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }


    public Project findProjectByProjectIdentifier(String identifier, String username){

        Project project = projectRepository.findByProjectIdentifier(identifier.toUpperCase());
        // check if it exists
        if(project == null){
            throw new ProjectIdException("Project "+ identifier +" does not exist!");
        }
        // check if the project id equals to username
        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username){
//        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

//        if(project == null){
//            throw new ProjectIdException("Project to be deleted not exists");
//        }

        projectRepository.delete(findProjectByProjectIdentifier(projectId,username));
    }

}
