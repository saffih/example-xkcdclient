package saffih.xkcdsolid;

        import java.util.Map;

public interface CountDict<T> {
    Map<T, Integer> getData();

    default void add(T key) {
        add(key, 1);
    }

    void add(T key, Integer cnt);
}
