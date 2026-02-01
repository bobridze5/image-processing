package imagecpu;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ImageUtils {
    private static int counter = 1;

    private ImageUtils() {
    }

    public static BufferedImage load(String path) {
        BufferedImage image = null;
        try {
            File file = new File(path);
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузки изображеня: " + path, e);
        }

        return image;
    }

    public static List<BufferedImage> load(String... paths) {
        List<BufferedImage> result = new ArrayList<>();
        for (String path : paths) {
            result.add(load(path));
        }

        return result;
    }

    public static BufferedImage toBufferedImage(byte[] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int[] rgb = new int[width * height];

        for (int i = 0; i < rgb.length; i++) {
            rgb[i] = (pixels[i] == 1) ? 0xFFFFFF : 0x000000;
        }

        image.setRGB(0, 0, width, height, rgb, 0, width);
        return image;
    }

    public static void save(BufferedImage image, String path) {
        try {
            File dir = new File(path);
            if (!dir.exists()) dir.mkdirs();

            String fileName;
            File file;
            do {
                fileName = "image_" + counter + ".png";
                file = new File(dir, fileName);
                counter++;
            } while (file.exists());

            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении изображения: " + e.getMessage());
        }
    }


}
