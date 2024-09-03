import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        String url = "https://myanimelist.net/anime/1735/Naruto__Shippuuden/reviews";
        List<Review> reviews = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();

            Elements reviewElements = doc.select(".body");

            for (Element reviewElement : reviewElements) {
                String username = reviewElement.select(".username").text();
                String text = reviewElement.select(".text").text();

                reviews.add(new Review(username, text));
            }



            Gson gson = new Gson();
            JsonArray jsonArray = new JsonArray();
            for (Review review : reviews) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("username", review.username);
                jsonObject.addProperty("text", review.text);
                jsonArray.add(jsonObject);
            }

            try (FileWriter file = new FileWriter("reviews.json")) {
                gson.toJson(jsonArray, file);
            }

            System.out.println("Reviews salvos em reviews.json");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
