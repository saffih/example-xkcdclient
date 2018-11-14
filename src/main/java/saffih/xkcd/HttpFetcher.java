package saffih.xkcd;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;

class HttpFetcher {
    public String getInfo(InfoBuilder target, int index){

        URI uri = target.getUri(index);
        return getContentAsString(uri);
    }

    private String getContentAsString(URI uri) {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpget = new HttpGet(uri);
        try (CloseableHttpResponse response = httpclient.execute(httpget)){
            if (response.getStatusLine().getStatusCode()!= HttpStatus.SC_OK) {
                return null;
            }
            return response.getEntity().getContent().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                XkcdClient.log.error("Can't close client ", e);
            }
        }
    }
}
