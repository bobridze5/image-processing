package imagecpu;

import java.util.ArrayList;
import java.util.List;

public interface Filter<V, R> {
    R filter(V value);

    default List<R> filter(List<V> values) {
        if (values == null) return null;
        List<R> results = new ArrayList<>(values.size());
        for (V value : values) {
            results.add(filter(value));
        }

        return results;
    }
}
