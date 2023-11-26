package com.monkeyteam.monkeycloud.repositories;

import com.monkeyteam.monkeycloud.entities.FavoriteFile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface FavoriteFileRepository extends CrudRepository<FavoriteFile, Long> {
    //Optional<User> findByUserId(Long userID);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM favorite_files WHERE user_id = ? and file_path = ?", nativeQuery = true)
    public void deleteFromFavorite(Long id, String filePath);
}