package io.github.shubhamtupe.respository;

import io.github.shubhamtupe.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITaskManagerRepository extends JpaRepository<Task, Long> {

    Optional<Task> findTaskByTitle(String title);
    
}
