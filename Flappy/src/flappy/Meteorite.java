package flappy;


public class Meteorite implements Runnable {
    public static Renderer renderer;
    private int FPS = 60;
    private long start, elapsed, wait, targetTime = 1000 / FPS, fps;

    public Meteorite(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void run() {
        while (true) {
            start = System.nanoTime();
            renderer.repaint();
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if (wait < 0) {
                wait = 16;
            }
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
