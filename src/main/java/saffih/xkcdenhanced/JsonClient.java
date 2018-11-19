package saffih.xkcdenhanced;

import com.google.gson.Gson;
import lombok.Data;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;
import java.util.stream.Stream;

//http://xkcd.com/info.0.json

public class JsonClient {
    Function<Integer, InputStream> source;

    public JsonClient(Function<Integer, InputStream> source) {
        this.source = source;
    }

    @Data
    public static class XkcdJson {
        /**
         * month : 1
         * num : 1
         * link :
         * year : 2006
         * news :
         * safe_title : Barrel - Part 1
         * transcript : [[A boy sits in a barrel which is floating in an ocean.]]
         * Boy: I wonder where I'll float next?
         * [[The barrel drifts into the distance. Nothing else can be seen.]]
         * {{Alt: Don't we all.}}
         * alt : Don't we all.
         * img : https://imgs.xkcd.com/comics/barrel_cropped_(1).jpg
         * title : Barrel - Part 1
         * day : 1
         */

        public String month;
        public int num;
        public String link;
        public String year;
        public String news;
        public String safe_title;
        public String transcript;
        public String alt;
        public String img;
        public String title;
        public String day;

        public static XkcdJson objectFromData(String str) {

            return new Gson().fromJson(str, XkcdJson.class);
        }

    }

    Stream<XkcdJson> getXkcdJson(Stream<Integer> indexs) {
        return indexs
                .parallel()
                .map(this::getXkcdJson);
    }

    XkcdJson getXkcdJson(Integer index) {
        try (InputStream inputStream = source.apply(index)) {
            String str = IOUtils.toString(inputStream, "UTF-8");
            return XkcdJson.objectFromData(str);
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
