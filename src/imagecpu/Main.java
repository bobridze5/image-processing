package imagecpu;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String outputPath = "images/results";
        Binarizer binarizer = new Binarizer(160);
        ImageFormatter formatter = new ImageFormatter(1);


        String[] images = {
                "images/1024x768.jpg",
                "images/1280x960.jpg",
                "images/2048x1536.jpg",
                "img.jpg"
        };

        List<BufferedImage> bufferedImages = ImageUtils.load(images);
        List<BinaryImageData> binaryImageDataList = binarizer.binarize(bufferedImages);

        for (int i = 0; i < binaryImageDataList.size(); i++) {
            BinaryImageData data = binaryImageDataList.get(i);

            int runs = 3;
            byte[] original = data.pixels();
            byte[] result = null;
            Timer[] timers = new Timer[runs];

            for (int k = 0; k < runs; k++) {
                byte[] copy = Arrays.copyOf(original, original.length);
                Timer timer = new Timer();

                timer.start();
                formatter.dilate(copy, data.width(), data.height());
                timer.end();

                timers[k] = timer;

                if (k == runs - 1) {
                    result = copy;
                }
            }

            BufferedImage image = ImageUtils.toBufferedImage(result, data.width(), data.height());
            ImageUtils.save(image, outputPath);

            StatisticsPrinter.print(images[i], timers);
        }
    }
}
