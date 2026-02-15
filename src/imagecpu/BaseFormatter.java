package imagecpu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseFormatter {
    protected final int threadCount;
    protected final ExecutorService executor;

    public BaseFormatter(int threadCount) {
        this.threadCount = threadCount;
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    protected List<ImageBlock> calculateBlocks(int width, int height) {
        List<ImageBlock> blocks = new ArrayList<>();

        int rows = (int) Math.sqrt(threadCount);
        int cols = (int) Math.ceil((double) threadCount / rows);

        int blockWidth = width / cols;
        int blockHeight = height / rows;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int startX = x * blockWidth;
                int endX = (x == cols - 1) ? width : (x + 1) * blockWidth;
                int startY = y * blockHeight;
                int endY = (y == rows - 1) ? height : (y + 1) * blockHeight;

                blocks.add(new ImageBlock(startX, endX, startY, endY));
            }
        }

        return blocks;
    }

    public void shutdown() {
        executor.shutdown();
    }
}
