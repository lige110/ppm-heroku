package com.lige110.springreact.ppmtool.repositories;

import com.lige110.springreact.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByProjectIdentifier(String identifier);

    Iterable<Project> findAllByProjectLeader(String username);

}
