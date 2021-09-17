package ru.job4j.job4j_url_shortcut;

import org.apache.commons.lang3.RandomStringUtils;
import org.hashids.Hashids;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {
    public static void main(String[] args) throws UnsupportedEncodingException, URISyntaxException {
        Hashids hashids = new Hashids("https://job4.ru",8);
        String hash = hashids.encode(1);
        System.out.println(hash);

//        String inp = "http%3a%2f%2fwww.google.com%2fpagead%2fconversion%2f1001680686%2f%3flabel%3d4dahCKKczAYQrt7R3QM%26value%3d%26muid%3d_0RQqV8nf-ENh3b4qRJuXQ%26bundleid%3dcom.google.android.youtube%26appversion%3d5.10";
//        System.out.println(inp);
//        String input = URLDecoder.decode(inp, StandardCharsets.UTF_8);
//        System.out.println(input);
//        Matcher m = Pattern.compile("(http[s]?)://([^/]+)(/[^\\?].*)?").matcher(input);
//        if (!m.matches()) return;
//        String protocol = m.group(1);
//        String domain   = m.group(2);
//        System.out.println(protocol + "://" + domain);
//        System.out.println(m.group(3));
//
//        java.net.URI uri = new java.net.URI(java.net.URLDecoder.decode(inp, "UTF-8"));
//
//        System.out.println( uri.getScheme() + "://" + uri.getHost() );
//        System.out.println(uri.getFragment());
//        System.out.println(uri.getPath());
//        System.out.println(uri.getQuery());

        String inp = "https://job4j.ru";
        java.net.URI uri = new java.net.URI(java.net.URLDecoder.decode(inp, "UTF-8"));
        System.out.println(uri.getScheme()+":"+uri.getHost()+":"+(uri.getPath().equals("")?"1":"2"));
        String result = RandomStringUtils.random(10, true, true);

        System.out.println(RandomStringUtils.randomAlphabetic(10));
        System.out.println("random = " + result);
    }
}
