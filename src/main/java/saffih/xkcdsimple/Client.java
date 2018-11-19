package saffih.xkcdsimple;

import com.google.gson.Gson;
import lombok.Data;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

//http://xkcd.com/info.0.json

public class Client {
    Gson gson = new Gson();

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

    XkcdJson getXkcdJson(Integer index) {
        try (InputStream source = getInputStream(index)) {
            String str = IOUtils.toString(source, "UTF-8");
            return XkcdJson.objectFromData(str);
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    CountDict<String> getTextCount(Integer index) {
        CountDict<String> result = new CountDict<>();
        StringBuilder builder = new StringBuilder();
        try (InputStream source = getInputStream(index)) {
            BufferedInputStream reader = new BufferedInputStream(source);
            while (true) {
                int c = reader.read();
                if (c == -1) {
                    break;
                }
                if (c == (int) ' ') {
                    result.add(builder.toString());
                    builder.setLength(0);
                    continue;
                }
                builder.append((char) c);
            }
        } catch (FileNotFoundException e) {
            // ignore
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        result.add(builder.toString());
        return result;
    }

    private InputStream getInputStream(Integer index) throws IOException {
        URL url = buildUrl(index);
        return url.openStream();
    }

    static String urlGetAsString(URL url) throws IOException {
        return IOUtils.toString(url, "UTF-8");
    }

    private URL buildUrl(Integer index) throws MalformedURLException {
        return new URL(String.format("https://xkcd.com/%s/info.0.json", index));
    }
}
