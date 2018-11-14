package saffih.xkcd;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

class XKCDInfoBuilder implements InfoBuilder {
    String urlPattern = "http://xkcd.com/%s/info.0.json";
    String currentUrl = "http://xkcd.com/info.0.json";

    @Override
    public URI getUri(int index) {
        try {
            if (index==0){
                return new URI(currentUrl);
            }
            return new URIBuilder(format(index)).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String format(int id) {
        return String.format(urlPattern, id);
    }
}
