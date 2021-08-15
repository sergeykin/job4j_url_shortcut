package ru.job4j.job4j_url_shortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.job4j_url_shortcut.model.Site;

public interface SiteRepository extends CrudRepository<Site, Integer> {
    Site findByLogin(String login);

    Site findBySite(String site);
}
