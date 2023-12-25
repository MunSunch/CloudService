package com.munsun.cloud_disk.repository;

import com.munsun.cloud_disk.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Integer>{
    Optional<File> findByName(String name);

    @Modifying(clearAutomatically = true)
    @Query(value = "update files set name = :newFilename where name = :oldFilename", nativeQuery = true)
    void replaceName(@Param("oldFilename") String oldName,
                     @Param("newFilename") String newName);
}
