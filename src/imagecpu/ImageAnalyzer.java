package imagecpu;

import java.awt.image.BufferedImage;
import java.util.List;

public class ImageAnalyzer {
    private final Binarizer<BufferedImage, BinaryImageData> binarizer;
    private final Formatter formatter;
    private final String outputPath;
    private final int testRuns;


    private ImageAnalyzer(Builder builder) {
        this.binarizer = builder.binarizer;
        this.formatter = builder.formatter;
        this.outputPath = builder.outputPath;
        this.testRuns = builder.testRuns;
    }

    public void analyze(String[] ImagesPaths) {
        List<BufferedImage> bufferedImages = ImageUtils.load(ImagesPaths);
        List<BinaryImageData> binaryImageDataList = binarizer.binarize(bufferedImages);

        for (int i = 0; i < binaryImageDataList.size(); i++) {
            BinaryImageData data = binaryImageDataList.get(i);

            byte[][] original = data.pixels();
            byte[][] result = null;
            Timer[] timers = new Timer[testRuns];

            for (int k = 0; k < testRuns; k++) {
                byte[][] copy = Utils.deepCopy(original);
                Timer timer = new Timer();

                timer.start();
                formatter.dilate(copy, data.width(), data.height());
                timer.end();

                timers[k] = timer;

                if (k == testRuns - 1) {
                    result = copy;
                }
            }

            BufferedImage image = ImageUtils.toBufferedImage(result, data.width(), data.height());
            ImageUtils.save(image, outputPath);

            StatisticsPrinter.print(ImagesPaths[i], timers);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Binarizer<BufferedImage, BinaryImageData> binarizer;
        private Formatter formatter;
        private String outputPath = "results";
        private int testRuns = 1;

        public Builder setBinarizer(Binarizer<BufferedImage, BinaryImageData> binarizer) {
            this.binarizer = binarizer;
            return this;
        }

        public Builder setFormatter(Formatter formatter) {
            this.formatter = formatter;
            return this;
        }

        public Builder setOutputPath(String path) {
            this.outputPath = path;
            return this;
        }


        public Builder setTestRuns(int testRuns) {
            this.testRuns = testRuns;
            return this;
        }

        public ImageAnalyzer build() {
            if (binarizer == null) binarizer = new ImageBinarizer(128);
            if (formatter == null) formatter = new ImageFormatter(1, Runtime.getRuntime().availableProcessors());
            return new ImageAnalyzer(this);
        }

    }
}
