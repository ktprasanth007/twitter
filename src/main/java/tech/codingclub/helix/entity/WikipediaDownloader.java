package tech.codingclub.helix.entity;

import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WikipediaDownloader {

    private String keyword;
    private String result = "";
    private String image_url = "";

    public WikipediaDownloader(String keyword) {
        this.keyword = keyword;
    }

    public WikiResult getResult() {

        // 1. clean the keyword
        // 2. get the URL for Wikipedia
        // 3. Make a Get request to Wikipedia
        // 4. Parse the useful results using Jsoup
        // 5. Show the results to the user

        if (keyword == null  || keyword.length()==0){
            return null;
        }

        // Step 1
        String cleanKeyword = keyword.trim().replaceAll("[ ]", "_");

        // Step 2
        String url = getWikipediaUrlForQuery(cleanKeyword);

        int state = 0;

        // Step 3
        try {
            String wikiResponse = HttpURLConnectionExample.sendGet(url);
//            System.out.println(wikiResponse);


            //Step 4
            Document document = Jsoup.parse(wikiResponse,"https://en.wikipedia.org/wiki/"); // Write the right hand part first then fill the datatype for that variable
            Elements elements = document.body().select(".mw-parser-output > *");

            for (Element element : elements) {
//                System.out.println(element.tagName());
                if (state == 0) {
                    if (element.tagName().equals("table")) {
                        state = 1;
                    }
                } else if (element.tagName().equals("p")) {
                    state = 2;
                    result = element.text();
                    break;
                }
                try {
                    image_url = document.body().select(".infobox img").get(0).attr("src");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (image_url.startsWith("//")){
                    image_url = "https:"+image_url;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        WikiResult wikiResult = new WikiResult(keyword, result, image_url);
//        System.out.println(new Gson().toJson(wikiResult));
//        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(wikiResult));

        return wikiResult;

    }

    public String getWikipediaUrlForQuery(String cleanKeyword){
        return "https://en.wikipedia.org/wiki/"+cleanKeyword;
    }

}
