package ru.job4j.job4j_url_shortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.job4j_url_shortcut.model.SiteLink;

public interface SiteLinkRepository extends CrudRepository<SiteLink, Integer> {
    SiteLink findByLink(String link);

    SiteLink findByHash(String hash);


    @Transactional
    @Modifying
    @Query("update SiteLink s set s.counter = s.counter+1 where s.hash = :hash")
    void appendCounter(@Param("hash") String hash);
}
