package imagecpu;

import java.util.ArrayList;
import java.util.List;

public interface Binarizer<V, R> {
    R binarize(V value);

    default List<R> binarize(List<V> values) {
        if (values == null) return null;
        List<R> results = new ArrayList<>(values.size());
        for (V value : values) {
            results.add(binarize(value));
        }

        return results;
    }
}
