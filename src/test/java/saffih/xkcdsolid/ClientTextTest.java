package saffih.xkcdsolid;

import org.apache.tools.ant.filters.StringInputStream;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ClientTextTest {
    private String st = "{\"month\": \"1\", \"num\": 1, \"link\": \"\", \"year\": \"2006\", \"news\": \"\", \"safe_title\": \"Barrel - Part 1\", \"transcript\": \"[[A boy sits in a barrel which is floating in an ocean.]]\\nBoy: I wonder where I'll float next?\\n[[The barrel drifts into the distance. Nothing else can be seen.]]\\n{{Alt: Don't we all.}}\", \"alt\": \"Don't we all.\", \"img\": \"https://imgs.scratchpad.com/comics/barrel_cropped_(1).jpg\", \"title\": \"Barrel - Part 1\", \"day\": \"1\"}";

    @Test
    public void testTextDry() {
        TextClient client = new TextClient(this::fakeSource);
        CountDict<String> count = client.getTextCount(1);
        System.out.print(count.getData());

        Assert.assertEquals(count.getData().get("wonder").intValue(), 1);
        Assert.assertEquals(count.getData().get("barrel").intValue(), 2);
    }

    @Test
    public void testFilteredTextDry() {
        TextClient client = new TextClient(this::fakeSource, this::fakeFiltered);
        CountDict<String> count = client.getTextCount(1);
        System.out.print(count.getData());

        Assert.assertEquals(count.getData().get("wonder").intValue(), 1);
        Assert.assertFalse(count.getData().containsKey("barrel"));
    }

    private CountDict<String> fakeFiltered() {
        return new FilteredCountDict<>(this::excludePart);
    }

    private  Boolean excludePart(String key) {
        return !"barrel".equals(key);
    }


    @Test
    public void testLotsTextDryParallel() {
        TextClient client = new TextClient(this::fakeSource);
        int endExclusive = 3000;
        List<CountDict<String>> dictCounts = IntStream.range(1, endExclusive).boxed()
                .parallel()
                .map(client::getTextCount).collect(Collectors.toList());
        CountDict<String> total = new CountDictSimple<>();
        dictCounts.forEach(count->count.getData().forEach(total::add));
        Assert.assertEquals(total.getData().get("wonder").intValue(), endExclusive-1);
    }

    @Test
    public void testTextLive() {
        TextClient client = new TextClient(new URLSource());
        CountDict<String> count = client.getTextCount(1);
        System.out.print(count.getData());

        Assert.assertEquals(count.getData().get("the").intValue(), 1);
    }

    @Test
    public void testLotsTextLiveParallel() {
        TextClient client = new TextClient(new URLSource());
        List<CountDict<String>> dictCounts = IntStream.range(1, 300).boxed()
                .parallel()
                .map(client::getTextCount)
                .collect(Collectors.toList());
        CountDict<String> total = new CountDictSimple<>();
        dictCounts.forEach(count->count.getData().forEach(total::add));
        Assert.assertEquals(total.getData().get("cat").intValue(), 4);
        Assert.assertEquals(total.getData().get("Holocaust").intValue(), 3);
    }

    private InputStream fakeSource(Integer index) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }
        return new StringInputStream(st);
    }

}
