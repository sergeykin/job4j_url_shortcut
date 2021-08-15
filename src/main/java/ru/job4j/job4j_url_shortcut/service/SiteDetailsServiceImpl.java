package ru.job4j.job4j_url_shortcut.service;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_url_shortcut.model.Site;
import ru.job4j.job4j_url_shortcut.repository.SiteRepository;

import static java.util.Collections.emptyList;

@Service
public class SiteDetailsServiceImpl implements UserDetailsService {
    private SiteRepository sites;

    public SiteDetailsServiceImpl(SiteRepository sites) {
        this.sites = sites;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Site site = sites.findByLogin(login);
        if (site == null) {
            throw new UsernameNotFoundException(login);
        }
        return new User(site.getLogin(), site.getPassword(), emptyList());
    }
}
