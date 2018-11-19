package saffih.xkcdsolid;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Function;

class URLSource implements Function<Integer, InputStream> {
    private InputStream getInputStream(Integer index) throws IOException {
        URL url = buildUrl(index);
        try {

            return url.openStream();
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    static String urlGetAsString(URL url) throws IOException {
        return IOUtils.toString(url, "UTF-8");
    }

    private URL buildUrl(Integer index) throws MalformedURLException {
        return new URL(String.format("https://xkcd.com/%s/info.0.json", index));
    }

    @Override
    public InputStream apply(Integer integer) {
        try {
            return getInputStream(integer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
