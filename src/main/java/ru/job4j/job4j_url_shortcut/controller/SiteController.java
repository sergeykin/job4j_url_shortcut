package ru.job4j.job4j_url_shortcut.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.job4j_url_shortcut.model.Site;
import ru.job4j.job4j_url_shortcut.repository.SiteRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class SiteController {
    private final SiteRepository siteRepository;
    private final BCryptPasswordEncoder encoder;

    public SiteController(SiteRepository siteRepository, BCryptPasswordEncoder encoder) {
        this.siteRepository = siteRepository;
        this.encoder = encoder;
    }

    @PostMapping("/registration")
    public ResponseEntity<Site> create(@RequestBody Site site) {
        Site tmp = siteRepository.findBySite(site.getSite());
        Map map = new HashMap<>() {{
            put("registration ", false);
            put("login", "");
            put("password ", "");
        }};
        if (tmp == null) {
            map.put("registration ", true);
            String login = RandomStringUtils.randomAlphabetic(10);
            String password = RandomStringUtils.random(10, true, true);
            site.setLogin(login);
            site.setPassword(encoder.encode(password));
            map.put("login", login);
            map.put("password ", password);
            siteRepository.save(site);
        }
        Object body = map;
        return new ResponseEntity(
                body,
                HttpStatus.CREATED
        );
    }
}
