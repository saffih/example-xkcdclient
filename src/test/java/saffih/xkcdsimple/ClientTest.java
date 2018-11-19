package saffih.xkcdsimple;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ClientTest {

    @Test
    public void testJson() {
        String st = "{\"month\": \"1\", \"num\": 1, \"link\": \"\", \"year\": \"2006\", \"news\": \"\", \"safe_title\": \"Barrel - Part 1\", \"transcript\": \"[[A boy sits in a barrel which is floating in an ocean.]]\\nBoy: I wonder where I'll float next?\\n[[The barrel drifts into the distance. Nothing else can be seen.]]\\n{{Alt: Don't we all.}}\", \"alt\": \"Don't we all.\", \"img\": \"https://imgs.scratchpad.com/comics/barrel_cropped_(1).jpg\", \"title\": \"Barrel - Part 1\", \"day\": \"1\"}";
        Client.XkcdJson obj = Client.XkcdJson.objectFromData(st);
        Assert.assertEquals(obj.year, "2006");
    }

    @Test
    public void testJsonLive() {
        Client client = new Client();
        Client.XkcdJson obj = client.getXkcdJson(1);
        Assert.assertEquals(obj.year, "2006");
    }

    @Test
    public void testLotsJsonLiveParallel() {
        Client client = new Client();
        List<Client.XkcdJson> lst = IntStream.range(1,100).boxed()
                .parallel()
                .map(client::getXkcdJson)
                .collect(Collectors.toList());
        Assert.assertEquals(lst.get(0).year, "2006");
        Assert.assertEquals(lst.get(98).title, "Binary Heart");
    }
    @Test
    public void testTextLive() {
        Client client = new Client();
        CountDict<String> count = client.getTextCount(1);
        System.out.print(count.data);

        Assert.assertEquals(count.data.get("the").intValue(), 1);
    }

    @Test
    public void testLotsTextLiveParallel() {
        Client client = new Client();
        List<CountDict<String>> dictCounts =  IntStream.range(1, 300).boxed()
                .parallel()
                .map(client::getTextCount).collect(Collectors.toList());
        CountDict<String> total = new CountDict<>();
        dictCounts.forEach(count->count.data.forEach(total::add));
        Assert.assertEquals(total.data.get("cat").intValue(), 4);
        Assert.assertEquals(total.data.get("Holocaust").intValue(), 3);
    }
}
