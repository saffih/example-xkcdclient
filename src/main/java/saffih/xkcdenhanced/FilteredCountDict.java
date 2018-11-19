package saffih.xkcdenhanced;

import java.util.function.Function;

public class FilteredCountDict<T> extends CountDictSimple<T> {
    private Function<T, Boolean> filterBy;

    public FilteredCountDict(Function<T, Boolean> filterBy) {
        this.filterBy = filterBy;
    }

    @Override
    public void add(T key, Integer cnt) {
        if (this.filterBy.apply(key)) {
            super.add(key, cnt);
        }
    }
}
