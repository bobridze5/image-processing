package imagecpu;

import java.awt.image.BufferedImage;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String outputPath = "images/results";
        Binarizer binarizer = new Binarizer(180);
        ImageFormatter formatter = new ImageFormatter(2);

        List<BufferedImage> bufferedImages = ImageUtils.load(
                "images/pic3.jpg",
                "img.jpg"
        );
        List<BinaryImageData> binaryImageDataList = binarizer.binarize(bufferedImages);

        for (BinaryImageData data : binaryImageDataList) {
            formatter.dilate(data.pixels(), data.width(), data.height());
            BufferedImage image = ImageUtils.toBufferedImage(data.pixels(), data.width(), data.height());
            ImageUtils.save(image, outputPath);
        }
        
    }
}
