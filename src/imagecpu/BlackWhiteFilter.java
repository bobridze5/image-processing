package imagecpu;

public class BlackWhiteFilter extends BaseFilter {
    private final int threshold;

    public BlackWhiteFilter(int threshold) {
        validateThreshold(threshold);
        this.threshold = threshold;
    }

    @Override
    protected byte handleIntensity(int intensity) {
        return (intensity > threshold) ? (byte) 1 : (byte) 0;
    }

    private void validateThreshold(int intensity) {
        if (intensity < 1 || intensity > 255) {
            throw new IllegalArgumentException("Значение порога должно быть в пределах от 1 до 255 включительно");
        }
    }


}
