package saffih.xkcdenhanced;

import org.apache.tools.ant.filters.StringInputStream;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ClientJsonTest {
    private String st2074 = "{\"month\": \"11\", \"num\": 2074, \"link\": \"\", \"year\": \"2018\", \"news\": \"\", \"safe_title\": \"Airplanes and Spaceships\", \"transcript\": \"\", \"alt\": \"Despite having now taken three months longer than the airplane people, we're making disappointingly little progress toward the obvious next stage of vehicle: The Unobtanium-hulled tunneling ship from the 2003 film 'The Core.'\", \"img\": \"https://imgs.xkcd.com/comics/airplanes_and_spaceships.png\", \"title\": \"Airplanes and Spaceships\", \"day\": \"19\"}";
    private String st1 = "{\"month\": \"1\", \"num\": 1, \"link\": \"\", \"year\": \"2006\", \"news\": \"\", \"safe_title\": \"Barrel - Part 1\", \"transcript\": \"[[A boy sits in a barrel which is floating in an ocean.]]\\nBoy: I wonder where I'll float next?\\n[[The barrel drifts into the distance. Nothing else can be seen.]]\\n{{Alt: Don't we all.}}\", \"alt\": \"Don't we all.\", \"img\": \"https://imgs.scratchpad.com/comics/barrel_cropped_(1).jpg\", \"title\": \"Barrel - Part 1\", \"day\": \"1\"}";
    private String st2 = "{\"month\": \"1\", \"num\": 2, \"link\": \"\", \"year\": \"2006\", \"news\": \"\", \"safe_title\": \"Petit Trees (sketch)\", \"transcript\": \"[[Two trees are growing on opposite sides of a sphere.]]\\n{{Alt-title: 'Petit' being a reference to Le Petit Prince, which I only thought about halfway through the sketch}}\", \"alt\": \"'Petit' being a reference to Le Petit Prince, which I only thought about halfway through the sketch\", \"img\": \"https://imgs.xkcd.com/comics/tree_cropped_(1).jpg\", \"title\": \"Petit Trees (sketch)\", \"day\": \"1\"}" ;

    private String st222="{\"month\": \"2\", \"num\": 222, \"link\": \"\", \"year\": \"2007\", \"news\": \"\", \"safe_title\": \"Small Talk\", \"transcript\": \"Sometimes I forget how to do small talk.\\n[[Two people are talking to each other]]\\nFirst person: Hey!\\nSecond person: Hey, man!\\nFirst person: What's up? How've you been?\\nSecond person: Well...\\n[[Nothing happens]]\\n[[Nothing happens]]\\n[[Nothing happens]]\\nFirst person: Uh, you okay?\\nSecond person: Yeah! It's just an interesting question. I'm trying to decide what best sums up my -\\nFirst person: <<SNAP>> Hey, conversation.\\nSecond person: Oh, right. I'm fine. You?\\n{{title text: But surely I owe you an accurate answer!}}\", \"alt\": \"But surely I owe you an accurate answer!\", \"img\": \"https://imgs.xkcd.com/comics/small_talk.png\", \"title\": \"Small Talk\", \"day\": \"12\"}";
    private String st223="{\"month\": \"2\", \"num\": 223, \"link\": \"\", \"year\": \"2007\", \"news\": \"\", \"safe_title\": \"Valentine's Day\", \"transcript\": \"{{Valentine's Day}}\\n[[There is a large, shaded, red heart.]]\\n{{Because love isn't quite complicated enough as it is.}}\\n{{alt: One of these days me and Joey Comeau will get around to subverting hetero-normative paradigm and fixing all this.}}\", \"alt\": \"One of these days me and Joey Comeau will get around to subverting the hetero-normative paradigm and fixing all this.\", \"img\": \"https://imgs.xkcd.com/comics/valentines_day.jpg\", \"title\": \"Valentine's Day\", \"day\": \"14\"}";
    private String st224="{\"month\": \"2\", \"num\": 224, \"link\": \"\", \"year\": \"2007\", \"news\": \"\", \"safe_title\": \"Lisp\", \"transcript\": \"[[Floating in space]]\\nSpeaker: Last night I drifted off while reading a Lisp book.\\nStick Figure Man: Huh?\\nSpeaker: Suddenly, I was bathed in a suffusion of blue.\\n[[Floating in space before a vast concept tree]]\\nSpeaker: At once, just like they said, I felt a great enlightenment.  I saw the naked structure of Lisp code unfold before me.\\nStick Figure Man: My God\\nStick Figure Man: It's full of 'car's\\nSpeaker: The patterns and metapatterns danced.  Syntax faded, and I swam in the purity of quantified conception.  Of ideas manifest.\\nTruly, this was the language from which the gods wrought the Universe.\\n[[Floating in space with God appearing through a line of clouds]]\\nGod: No, it's not.\\nStick Figure Man: It's not?\\nGod: I mean, ostensibly, yes.  Honestly, we hacked most of it together with Perl.\\n{{Alt Text: We lost the documentation on quantum mechanics.  You'll have to decode the regexes yourself.}}\", \"alt\": \"We lost the documentation on quantum mechanics.  You'll have to decode the regexes yourself.\", \"img\": \"https://imgs.xkcd.com/comics/lisp.jpg\", \"title\": \"Lisp\", \"day\": \"16\"}";
    private List<String> all = Stream.of(st222, st1, st2074, st2, st224, st223).collect(Collectors.toList());



    @Test
    public void testJson() {
        JsonClient.XkcdJson obj = JsonClient.XkcdJson.objectFromData(st1);
        Assert.assertEquals(obj.year, "2006");
    }

    @Test
    public void testJsonDry() {
        JsonClient client = new JsonClient( this::allListSupplier);
        JsonClient.XkcdJson obj = client.getXkcdJson(1);
        Assert.assertEquals(obj.year, "2006");
    }

    private InputStream allListSupplier(Integer index) {
        return new StringInputStream(all.get(index%all.size()));
    }

    @Test
    public void testJsonSort() {
        JsonClient client = new JsonClient(this::allListSupplier);

        Stream<JsonClient.XkcdJson> items = client.getXkcdJson(IntStream.range(1,all.size()+1).boxed()).sorted(this::compareToByMonthYearTitle);
        List<JsonClient.XkcdJson> result = items.collect(Collectors.toList());
        Assert.assertEquals(result.get(0).month, "1");
        Assert.assertEquals(result.get(0).year, "2006");
        Assert.assertEquals(result.get(1).month, "1");
        Assert.assertEquals(result.get(1).year, "2006");
        Assert.assertEquals(result.get(2).year, "2007");
        Assert.assertEquals(result.get(3).year, "2007");
    }

    private int compareToByMonthYearTitle(JsonClient.XkcdJson a, JsonClient.XkcdJson b){
        int result;
        result = Integer.parseInt(a.month) - Integer.parseInt(b.month);
        if (result!=0) return result;
        result = a.year.compareToIgnoreCase(b.year);
        if (result!=0) return result;
        result = a.title.compareToIgnoreCase(b.title);
        if (result!=0) return result;
        return result;
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
        Stream<Integer> indexs = IntStream.range(1, 100).boxed();
        List<JsonClient.XkcdJson> lst = client.getXkcdJson(indexs).collect(Collectors.toList());
        Assert.assertEquals(lst.get(0).year, "2006");
        Assert.assertEquals(lst.get(98).title, "Binary Heart");
    }

}
