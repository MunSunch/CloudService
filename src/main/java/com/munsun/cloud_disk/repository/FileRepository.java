package com.munsun.cloud_disk.repository;

import com.munsun.cloud_disk.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Integer>{
    Optional<File> findByName(String name);

    @Modifying
    @Query(value = "UPDATE files set name = ?2 where name = ?1", nativeQuery = true)
    void replaceName(String oldName, String newName);
}
