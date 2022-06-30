package com.example.simplejparest.repository;

import com.example.simplejparest.data.World;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "worlds", path = "worlds")
public interface WorldRepository extends CrudRepository<World, Long> {

    World findByName(@Param("name") String name);

}
