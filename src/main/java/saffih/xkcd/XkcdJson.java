package saffih.xkcd;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class XkcdJson {


    /**
     * month : 11
     * num : 2071
     * link :
     * year : 2018
     * news :
     * safe_title : Indirect Detection
     * transcript :
     * alt : I'm like a prisoner in Plato's Cave, seeing only the shade you throw on the wall.
     * img : https://imgs.xkcd.com/comics/indirect_detection.png
     * title : Indirect Detection
     * day : 12
     */

    private String month;
    private int num;
    private String link;
    private String year;
    private String news;
    private String safe_title;
    private String transcript;
    private String alt;
    private String img;
    private String title;
    private String day;

    public static XkcdJson objectFromData(String str) {

        return new Gson().fromJson(str, XkcdJson.class);
    }

    public static XkcdJson objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), XkcdJson.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<XkcdJson> arrayXkcdJsonFromData(String str) {

        Type listType = new TypeToken<ArrayList<XkcdJson>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<XkcdJson> arrayXkcdJsonFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<XkcdJson>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getSafe_title() {
        return safe_title;
    }

    public void setSafe_title(String safe_title) {
        this.safe_title = safe_title;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
