package imagecpu;

import java.awt.image.BufferedImage;

public abstract class BaseFilter implements Filter<BufferedImage, BinaryImageData> {
    @Override
    public BinaryImageData filter(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        byte[][] pixels = new byte[h][w];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                int intensity = (r + g + b) / 3;

                pixels[y][x] = handleIntensity(intensity);
            }
        }

        return new BinaryImageData(pixels, w, h);
    }

    protected abstract byte handleIntensity(int intensity);
}
