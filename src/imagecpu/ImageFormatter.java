package imagecpu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class ImageFormatter extends BaseFormatter {
    public ImageFormatter() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public ImageFormatter(int threadCount) {
        super(threadCount);
    }

    public void sharpness(ImageData data) {
        if (data.isColor()) {
            sharpness(data.getRed(), data.getWidth(), data.getHeight());
            sharpness(data.getGreen(), data.getWidth(), data.getHeight());
            sharpness(data.getBlue(), data.getWidth(), data.getHeight());
        } else {
            sharpness(data.getRed(), data.getWidth(), data.getHeight());
        }
    }


    public void sharpness(byte[][] pixels, int width, int height) {
        byte[][] source = Utils.deepCopy(pixels);

        float[][] kernel = {
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };

        List<ImageBlock> blocks = calculateBlocks(width, height);
//        List<Thread> threads = new ArrayList<>();
        List<Future<?>> futures = new ArrayList<>();

        for (ImageBlock block : blocks) {
            futures.add(executor.submit(() -> {
                for (int y = Math.max(1, block.startY()); y < Math.min(height - 1, block.endY()); y++) {
                    for (int x = Math.max(1, block.startX()); x < Math.min(width - 1, block.endX()); x++) {

                        float sum = 0;
                        for (int dy = 0; dy < 3; dy++) {
                            for (int dx = 0; dx < 3; dx++) {
                                int val = source[y + dy - 1][x + dx - 1] & 0xFF;
                                sum += val * kernel[dy][dx];
                            }
                        }
                        pixels[y][x] = (byte) Math.max(0, Math.min(255, (int) sum));
                    }
                }
            }));
        }

        waitFor(futures);

    }


    public void dilate(ImageData data, int step) {
        if (data.isColor()) {
            dilate(data.getRed(), data.getWidth(), data.getHeight(), step);
            dilate(data.getGreen(), data.getWidth(), data.getHeight(), step);
            dilate(data.getBlue(), data.getWidth(), data.getHeight(), step);
        } else {
            dilate(data.getRed(), data.getWidth(), data.getHeight(), step);
        }
    }

    public void dilate(byte[][] pixels, int width, int height, int step) {
        validateStep(step);
        byte[][] source = Utils.deepCopy(pixels);
        if (threadCount > 1) {
            applyDilateParallel(source, pixels, width, height, step);
        } else {
            applyDilate(source, pixels, 0, width, 0, height, step);
        }

    }

    private void applyDilateParallel(byte[][] source, byte[][] target, int width, int height, int step) {
        int rows = (int) Math.sqrt(threadCount);
        int cols = (int) Math.ceil((double) threadCount / rows);

        int tileWidth = width / cols;
        int tileHeight = height / rows;

        List<Thread> threads = new ArrayList<>();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (threads.size() >= threadCount) break;

                int xStart = x * tileWidth;
                int xEnd = (x == cols - 1) ? width : (x + 1) * tileWidth;
                int yStart = y * tileHeight;
                int yEnd = (y == rows - 1) ? height : (y + 1) * tileHeight;

                Thread thread = new Thread(() -> applyDilate(source, target, xStart, xEnd, yStart, yEnd, step));
                threads.add(thread);
            }
        }

        threads.forEach(Thread::start);

        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void applyDilate(
            byte[][] source,
            byte[][] target,
            int xStart,
            int xEnd,
            int yStart,
            int yEnd,
            int step
    ) {
        int width = source[0].length;
        int height = source.length;

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                if (source[y][x] != 0) {
                    for (int dy = -step; dy <= step; dy++) {
                        for (int dx = -step; dx <= step; dx++) {
                            int nx = x + dx;
                            int ny = y + dy;

                            if (nx < 0 || nx >= width || ny < 0 || ny >= height) {
                                continue;
                            }

                            target[ny][nx] = (byte) 255;
                        }
                    }
                }
            }
        }
    }

    private void validateStep(int step) {
        if (step < 1 || step > 3) {
            throw new IllegalArgumentException("Шаг должен быть 1, 2 или  3");
        }
    }

    private void waitFor(List<Future<?>> futures) {
        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
