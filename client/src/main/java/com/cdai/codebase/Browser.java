package com.cdai.codebase;

import com.cdai.codebase.client.Client;
import com.cdai.codebase.util.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.CountDownLatch;

/**
 */
public class Browser {

    private Client client;

    public Browser(Client client) {
        this.client = client;
    }

    public String loadResource(String url, String resource) {
        try {
            client.start();

            // Load HTML first
            String html = client.get(url + resource);
            Utils.debug("Load web page [%s] complete.", resource);
            Utils.debug("HTML content: %s", html);

            // Load CSS and JS in multiplexing way
            Document doc = Jsoup.parse(html);
            Elements links = doc.select("link");
            Elements scripts = doc.select("script");
            final CountDownLatch latch = new CountDownLatch(links.size() + scripts.size());
            for (Element elt : links) {
                final String href = elt.attr("href");
                Utils.debug("Load Css [%s] starts...", href);
                client.get(url + href, content -> {
                    Utils.debug("Load Css [%s] complete", href);
                    latch.countDown();
                });
            }
            for (Element elt : scripts) {
                final String href = elt.attr("src");
                Utils.debug("Load Js [%s] starts...", href);
                client.get(url + href, content -> {
                    Utils.debug("Load Js [%s] complete", href);
                    latch.countDown();
                });
            }
            latch.await();

            client.stop();
            return html;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
