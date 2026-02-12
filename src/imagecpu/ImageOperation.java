package imagecpu;

@FunctionalInterface
public interface ImageOperation {
    void apply(byte[][] pixels, int width, int height);
}
