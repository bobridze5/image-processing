package imagecpu;

import java.awt.image.BufferedImage;
import java.util.List;

public class ImageAnalyzer {
    private final Filter<BufferedImage, BinaryImageData> filter;
    private final ImageOperation operation;
    private final String outputPath;
    private final int testRuns;


    private ImageAnalyzer(Builder builder) {
        this.filter = builder.filter;
        this.operation = builder.operation;
        this.outputPath = builder.outputPath;
        this.testRuns = builder.testRuns;
    }

    public void analyze(String[] ImagesPaths) {
        List<BufferedImage> bufferedImages = ImageUtils.load(ImagesPaths);
        List<BinaryImageData> binaryImageDataList = filter.filter(bufferedImages);

        for (int i = 0; i < binaryImageDataList.size(); i++) {
            BinaryImageData data = binaryImageDataList.get(i);

            byte[][] original = data.pixels();
            byte[][] result = null;
            Timer[] timers = new Timer[testRuns];

            for (int k = 0; k < testRuns; k++) {
                byte[][] copy = Utils.deepCopy(original);
                Timer timer = new Timer();

                timer.start();
                operation.apply(copy, data.width(), data.height());
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
        private Filter<BufferedImage, BinaryImageData> filter;
        private ImageFormatter formatter;
        private ImageOperation operation;
        private String outputPath = "results";
        private int testRuns = 1;

        public Builder setFilter(Filter<BufferedImage, BinaryImageData> filter) {
            this.filter = filter;
            return this;
        }

        public Builder setFormatter(ImageFormatter formatter) {
            this.formatter = formatter;
            return this;
        }

        public Builder setOperationDilate(int step) {
            this.operation = (pixels, width, height) -> formatter.dilate(pixels, width, height, step);
            return this;
        }

        public Builder setOperationConvolution(float[][] kernel) {
            this.operation = (pixels, width, height) -> formatter.convolve(pixels, width, height);
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
            if (filter == null) throw new IllegalStateException("Не задан фильтр!");
            if (formatter == null) formatter = new ImageFormatter();
            if (operation == null) throw new IllegalStateException("Не задана операция!");
            return new ImageAnalyzer(this);
        }

    }
}
