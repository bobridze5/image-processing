package imagecpu;

public abstract class BaseFormatter {
    protected final int threadCount;

    public BaseFormatter(int threadCount) {
        this.threadCount = threadCount;
    }
}
