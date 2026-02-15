package imagecpu;

public class BlackWhiteFilter extends BaseFilter {
    private final int threshold;

    public BlackWhiteFilter(int threshold) {
        validateThreshold(threshold);
        this.threshold = threshold;
    }

    @Override
    protected void handleChannels(byte[][] rA, byte[][] gA, byte[][] bA, int x, int y, int r, int g, int b) {
        int intensity = (r + g + b) / 3;
        byte value = (intensity > threshold) ? (byte) 255 : (byte) 0;
        rA[y][x] = gA[y][x] = bA[y][x] = value;
    }

    private void validateThreshold(int intensity) {
        if (intensity < 1 || intensity > 255) {
            throw new IllegalArgumentException("Значение порога должно быть в пределах от 1 до 255 включительно");
        }
    }


}
