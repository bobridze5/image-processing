package imagecpu;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Binarizer {
    private final int threshold;

    public Binarizer(int threshold) {
        validateThreshold(threshold);
        this.threshold = threshold;
    }

    public List<BinaryImageData> binarize(List<BufferedImage> images) {
        List<BinaryImageData> result = new ArrayList<>();
        for (BufferedImage bf : images) {
            result.add(binarize(bf));
        }

        return result;
    }

    public BinaryImageData binarize(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        byte[] pixels = new byte[h * w];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = image.getRGB(x, y);
                pixels[y * w + x] = transformPixel(rgb);
            }
        }

        return new BinaryImageData(pixels, w, h);
    }


    private byte transformPixel(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        int intensity = (r + g + b) / 3;
        return (intensity > threshold) ? (byte) 1 : (byte) 0;
    }

    private void validateThreshold(int intensity) {
        if (intensity < 1 || intensity > 255) {
            throw new IllegalArgumentException("Значение порога должно быть в пределах от 1 до 255 включительно");
        }
    }


}
