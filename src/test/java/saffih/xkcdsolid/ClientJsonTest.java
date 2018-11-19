package saffih.xkcdsolid;

import org.apache.tools.ant.filters.StringInputStream;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ClientJsonTest {
    private String st = "{\"month\": \"1\", \"num\": 1, \"link\": \"\", \"year\": \"2006\", \"news\": \"\", \"safe_title\": \"Barrel - Part 1\", \"transcript\": \"[[A boy sits in a barrel which is floating in an ocean.]]\\nBoy: I wonder where I'll float next?\\n[[The barrel drifts into the distance. Nothing else can be seen.]]\\n{{Alt: Don't we all.}}\", \"alt\": \"Don't we all.\", \"img\": \"https://imgs.scratchpad.com/comics/barrel_cropped_(1).jpg\", \"title\": \"Barrel - Part 1\", \"day\": \"1\"}";

    @Test
    public void testJson() {
        JsonClient.XkcdJson obj = JsonClient.XkcdJson.objectFromData(st);
        Assert.assertEquals(obj.year, "2006");
    }

    @Test
    public void testJsonDry() {
        JsonClient client = new JsonClient(new FakeSource(st));
        JsonClient.XkcdJson obj = client.getXkcdJson(1);
        Assert.assertEquals(obj.year, "2006");
    }

    @Test
    public void testJsonLive() {
        JsonClient client = new JsonClient(new URLSource());
        JsonClient.XkcdJson obj = client.getXkcdJson(1);
        Assert.assertEquals(obj.year, "2006");
    }

    @Test
    public void testLotsJsonLiveParallel() {
        JsonClient client = new JsonClient(new URLSource());
        Stream<JsonClient.XkcdJson> objectStream = IntStream.range(1,100).boxed()
                .parallel()
                .map(client::getXkcdJson);
        List<JsonClient.XkcdJson> lst = objectStream.collect(Collectors.toList());
        Assert.assertEquals(lst.get(0).year, "2006");
        Assert.assertEquals(lst.get(98).title, "Binary Heart");
    }

    private class FakeSource implements Function<Integer, InputStream> {
        public FakeSource(String st) {
            this.st = st;
        }

        String st;
        @Override
        public InputStream apply(Integer integer) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            return new StringInputStream(st);
        }
    }
}
