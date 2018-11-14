package saffih.xkcd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * http://xkcd.com/info.0.json (current comic)
 */
public class XkcdClient {
    private static final String TAG = "XkcdClient";
    static Log log = LogFactory.getLog(TAG);
    private final HttpFetcher fetcher;
    InfoBuilder target;

    public XkcdClient(InfoBuilder target) {
        this.target = target;
        this.fetcher = new HttpFetcher();
    }

    public XkcdJson getInfo(int index) {

        String content = this.fetcher.getInfo(target, index);
        if (content == null) {
            return null;
        }
        return XkcdJson.objectFromData(content);
    }

}
