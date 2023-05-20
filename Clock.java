package Code;

public class Clock {
    private float millisPerCycle;
    private long lastUpdate;
    private int elapsedCycles;
    private float excessCycles;
    private boolean isPaused;

    public Clock(float cyclesPerSecond) {
        setCyclesPerSecond(cyclesPerSecond);
        reset();
    }

    public void setCyclesPerSecond(float cyclesPerSecond) {
        this.millisPerCycle = (1.0f / cyclesPerSecond) * 1000;
    }
    
    //Reset thoi gian
    public void reset() {
        this.elapsedCycles = 0;
        this.excessCycles = 0.0f;
        this.lastUpdate = getCurrentTime();
        this.isPaused = false;
    }

    //Cap nhat thoi gian 
    public void update() {
        long currUpdate = getCurrentTime();
        float delta = (float)(currUpdate - lastUpdate) + excessCycles;

        if(!isPaused) {
            this.elapsedCycles += (int)Math.floor(delta / millisPerCycle);
            this.excessCycles = delta % millisPerCycle;
        }

        this.lastUpdate = currUpdate;
    }

    //Thay doi trang thai thoi gian
    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    //Lay trang thai thoi gian
    public boolean isPaused() {
        return isPaused;
    }

    
    public boolean hasElapsedCycle() {
        if(elapsedCycles > 0) {
            this.elapsedCycles--;
            return true;
        }
        return false;
    }

    public boolean peekElapsedCycle() {
        return (elapsedCycles > 0);
    }

    private static final long getCurrentTime() {
        return (System.nanoTime() / 1000000L);
    }

}