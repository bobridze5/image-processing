package imagecpu;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageLoader {
    private final List<String> paths;
    private final int threshold;

    public ImageLoader(int threshold, String... paths) {
        validateThreshold(threshold);
        this.paths = List.of(paths);
        this.threshold = threshold;
    }


    public List<BinaryImageData> getImageData() {
        List<BinaryImageData> data = new ArrayList<>();
        for (String path : paths) {
            BufferedImage image = getImage(path);

            if (image == null) {
                throw new IllegalStateException("Не удалось загрузить изображение: " + path);
            }

            BinaryImageData imageData = convertImageToImageData(image);
            data.add(imageData);
        }


        return data;
    }

    private void validateThreshold(int intensity) {
        if (intensity < 1 || intensity > 255) {
            throw new IllegalArgumentException("Значение порога должно быть в пределах от 1 до 255 включительно");
        }
    }

    private BinaryImageData convertImageToImageData(BufferedImage image) {
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

    private BufferedImage getImage(String path) {
        BufferedImage image = null;
        try {
            File file = new File(path);
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузки изображеня: " + path, e);
        }

        return image;
    }

    private byte transformPixel(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        int intensity = (r + g + b) / 3;
        return (intensity > threshold) ? (byte) 1 : (byte) 0;
    }

}
