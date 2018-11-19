package saffih.xkcdsolid;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;
import java.util.function.Supplier;

//http://xkcd.com/info.0.json

public class TextClient {
    Function<Integer, InputStream> source;
    Supplier<CountDict<String>> dictCreator;

    public TextClient(Function<Integer, InputStream> source) {
        this(source, CountDictSimple::new);
    }
    public TextClient(Function<Integer, InputStream> source, Supplier<CountDict<String>> dictCreator) {
        this.source = source;
        this.dictCreator = dictCreator;
    }


    CountDict<String> getTextCount(Integer index) {
        CountDict<String> result = this.dictCreator.get();
        StringBuilder builder = new StringBuilder();
        try (InputStream inputStream = source.apply(index)) {
            BufferedInputStream reader = new BufferedInputStream(inputStream);
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

}
