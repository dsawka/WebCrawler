package pl.coderslab;

import com.github.slugify.Slugify;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Main {

    static final Slugify slg = Slugify.builder().build();
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.infoworld.com/category/java/").get();
        Elements newsHeadlines = doc.select("h3 a");
        for (Element headline : newsHeadlines) {
            String articleUrl = headline.absUrl("href");
            Document article = Jsoup.connect(articleUrl).get();
            Elements articleTitle = article.select("h1");
            String filename = String.join("", UUID.randomUUID().toString(),"-",slg.slugify(articleTitle.text()),".txt");
            Elements story = article.select("p");
            FileUtils.writeStringToFile(new File("articles/" + filename), story.text());
        }
    }

}