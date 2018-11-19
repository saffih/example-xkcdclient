package saffih.xkcdenhanced;

import java.util.HashMap;
import java.util.Map;

public class CountDictSimple<T> implements CountDict<T> {
    private Map<T, Integer> data = new HashMap<>();

    @Override
    public Map<T, Integer> getData() {
        return data;
    }


    @Override
    public void add(T key, Integer cnt) {
        getData().put(key, getData().getOrDefault(key, 0) + cnt);
    }
}
