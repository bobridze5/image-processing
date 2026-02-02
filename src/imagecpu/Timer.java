package imagecpu;

public final class Timer {
    private long startTime = -1;
    private long endTime = -1;
    private boolean running = false;

    public void start() {
        startTime = System.nanoTime();
        running = true;
    }

    public void end() {
        if (!running) {
            throw new IllegalStateException("Таймер не был запущен");
        }
        endTime = System.nanoTime();
        running = false;
    }

    public long getDurationNano() {
        if (startTime == -1) throw new IllegalStateException("Замер ещё не начинался");
        if (running) throw new IllegalStateException("Таймер ещё работает");
        return endTime - startTime;
    }

    public double getDurationMs() {
        return getDurationNano() / 1_000_000.0;
    }

    public double getDurationSec(){
        return getDurationMs() / 1000.0;
    }

    public long getStart() {
        return startTime;
    }

    public long getEnd() {
        return endTime;
    }
}
