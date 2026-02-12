package imagecpu;

import java.awt.image.BufferedImage;

public abstract class BaseFilter implements Filter<BufferedImage, ImageData> {
    @Override
    public ImageData filter(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        byte[][] rA = new byte[h][w];
        byte[][] gA = new byte[h][w];
        byte[][] bA = new byte[h][w];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = image.getRGB(x, y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                handleChannels(rA, gA, bA, x, y, r, g, b);
            }
        }

        return new ImageData(rA, gA, bA, w, h);
    }

    protected abstract void handleChannels(
            byte[][] rA, byte[][] gA, byte[][] bA,
            int x, int y,
            int r, int g, int b
    );
}
