package imagecpu;

public class InversionFilter extends BaseFilter {
    @Override
    protected void handleChannels(byte[][] rA, byte[][] gA, byte[][] bA, int x, int y, int r, int g, int b) {
        rA[y][x] = (byte) (255 - r);
        gA[y][x] = (byte) (255 - g);
        bA[y][x] = (byte) (255 - b);
    }
}
