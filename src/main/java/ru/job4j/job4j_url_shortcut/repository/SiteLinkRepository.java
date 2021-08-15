package ru.job4j.job4j_url_shortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.job4j_url_shortcut.model.SiteLink;

public interface SiteLinkRepository extends CrudRepository<SiteLink, Integer> {
    SiteLink findByLink(String link);

    SiteLink findByHash(String hash);
}
