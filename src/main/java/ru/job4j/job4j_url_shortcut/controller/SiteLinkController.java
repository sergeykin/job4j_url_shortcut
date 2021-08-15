package ru.job4j.job4j_url_shortcut.controller;

import org.hashids.Hashids;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_url_shortcut.model.SiteLink;
import ru.job4j.job4j_url_shortcut.model.Url;
import ru.job4j.job4j_url_shortcut.repository.SiteLinkRepository;
import ru.job4j.job4j_url_shortcut.repository.SiteRepository;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class SiteLinkController {

    private final SiteLinkRepository siteLinkRepository;
    private final SiteRepository siteRepository;

    public SiteLinkController(SiteLinkRepository siteLinkRepository, SiteRepository siteRepository) {
        this.siteLinkRepository = siteLinkRepository;
        this.siteRepository = siteRepository;
    }

    @PostMapping("/convert")
    public ResponseEntity<SiteLink> create(@RequestBody Url url) throws UnsupportedEncodingException, URISyntaxException {
        SiteLink siteLink;
        URI uri = new URI(java.net.URLDecoder.decode(url.getUrl(), "UTF-8"));
        String cleanURL = uri.getScheme()+"://"+uri.getHost()+"/"+uri.getPath();
        siteLink = siteLinkRepository.findByLink(cleanURL);
        String hash;
        if (siteLink == null) {
            siteLink = new SiteLink();
            siteLink.setLink(cleanURL);
            siteLink.setSite(siteRepository.findBySite(uri.getHost()));
            Hashids hashids = new Hashids(uri.getHost(),8);
            siteLinkRepository.save(siteLink);
            siteLink.setHash(hashids.encode(siteLink.getId()));
            siteLinkRepository.save(siteLink);
        }

        SiteLink finalSiteLink = siteLink;
        Map map = new HashMap<>() {{
            put("code ", finalSiteLink.getHash());
        }};
        Object body = map;
        return new ResponseEntity(
                body,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/redirect/{hash}")
    public ResponseEntity<SiteLink> redirect(@PathVariable String hash) {
        SiteLink siteLink = siteLinkRepository.findByHash(hash);
        siteLink.setCounter(siteLink.getCounter() + 1);
        siteLinkRepository.save(siteLink);
        return new ResponseEntity(
                new MultiValueMapAdapter<>(Map.of("REDIRECT", List.of(siteLink.getLink()))),
                HttpStatus.MOVED_TEMPORARILY
        );
    }

    @GetMapping("/statistic")
    public ResponseEntity<SiteLink> statistic() {
        List<Map> list = new ArrayList<>();
        for (SiteLink sitelink:siteLinkRepository.findAll()) {
            Map map = new HashMap<>() {{
                put("url ", sitelink.getLink());
                put("total ", sitelink.getCounter());
            }};
            list.add(map);
        }
        Object body = list;
        return new ResponseEntity(
                body
                ,HttpStatus.OK
        );
    }
}
