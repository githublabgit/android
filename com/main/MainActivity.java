package com.main;

public class MainActivity extends android.app.Activity   
{
    @Override  
    public void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final var webView = new android.webkit.WebView(this);
        try
        {
            final var document = org.jsoup.Jsoup.connect("https://rumble.com/user/chaowenguo1").get();
            for (final var href:(java.lang.Iterable<java.lang.String>)document.select("a.videostream__link.link").stream().map($ -> $.attr("abs:href"))::iterator)
            {
                final var rumble = org.jsoup.Jsoup.connect(href).get();
                final var video = new java.net.URI(rumble.select("link[type='application/json+oembed']").attr("href")).getQuery().split("/");
                final var a = org.jsoup.Jsoup.connect(java.lang.String.join("?", "https://rumble.com/embedJS/u3", java.util.Map.ofEntries(java.util.Map.entry("request", "video"), java.util.Map.entry("ver", "2"), java.util.Map.entry("v", video[video.length - 1]), java.util.Map.entry("ext", java.net.URLEncoder.encode("{\"ad_count\":null}", java.nio.charset.StandardCharsets.UTF_8.toString())), java.util.Map.entry("ad_wt", "1")).entrySet().stream().map($ -> java.lang.String.join("=", $.getKey(), $.getValue())).collect(java.util.stream.Collectors.joining("&")))).ignoreContentType(true).get();
                final var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                android.util.Log.v("1", objectMapper.readTree(a.text()).get("a").get("ads").get(0).get("waterfall").get(0).get("url").asText());
            }
            webView.loadDataWithBaseURL(null, document.title(), "text/html",  "utf-8", null);
        }
        catch (final java.lang.Exception e){}
        super.setContentView(webView);
    }  
}
