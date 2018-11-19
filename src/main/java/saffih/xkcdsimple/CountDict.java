package saffih.xkcdsimple;

import java.util.HashMap;
import java.util.Map;

public class CountDict<T> {
    Map<T, Integer> data = new HashMap<>();

    void add(T key) {
        add(key, 1  );
    }
    void add(T key, Integer cnt) {
        data.put(key, data.getOrDefault(key, 0) + cnt);
    }

}
