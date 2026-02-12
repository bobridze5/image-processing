package imagecpu;

@FunctionalInterface
public interface ImageOperation {
    void apply(ImageData data);
}
