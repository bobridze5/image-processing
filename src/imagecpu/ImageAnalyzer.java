package imagecpu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImageAnalyzer {
    private final Filter<BufferedImage, ImageData> filter;
    private final ImageOperation operation;
    private final ImageFormatter formatter;
    private final String outputPath;
    private final int testRuns;


    private ImageAnalyzer(Builder builder) {
        this.filter = builder.filter;
        this.operation = builder.operation;
        this.outputPath = builder.outputPath;
        this.testRuns = builder.testRuns;
        this.formatter = builder.formatter;
    }

    public void analyze(String[] ImagesPaths) {
        List<BufferedImage> bufferedImages = ImageUtils.load(ImagesPaths);
        List<ImageData> imageDataList = filter.filter(bufferedImages);

        for (int i = 0; i < imageDataList.size(); i++) {
            ImageData data = imageDataList.get(i);
            ImageData result = null;
            Timer[] timers = new Timer[testRuns];

            for (int k = 0; k < testRuns; k++) {
                ImageData copy = Utils.deepCopy(data);
                Timer timer = new Timer();

                timer.start();
                operation.apply(copy);
                timer.end();

                timers[k] = timer;

                if (k == testRuns - 1) {
                    result = copy;
                }
            }

            BufferedImage image = ImageUtils.toBufferedImage(result);
            ImageUtils.save(image, outputPath);

            StatisticsPrinter.print(ImagesPaths[i], timers);
        }

        formatter.shutdown();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Filter<BufferedImage, ImageData> filter;
        private ImageFormatter formatter;
        private ImageOperation operation;
        private String outputPath = "results";
        private int testRuns = 1;

        public Builder setFilter(Filter<BufferedImage, ImageData> filter) {
            this.filter = filter;
            return this;
        }

        public Builder setFormatter(ImageFormatter formatter) {
            this.formatter = formatter;
            return this;
        }

        public Builder setOperationDilate(int step) {
            this.operation = (data) -> formatter.dilate(data, step);
            return this;
        }

        public Builder setOperationSharpness() {
            this.operation = (data) -> formatter.sharpness(data);
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
