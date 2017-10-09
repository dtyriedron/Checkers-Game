public class GameLoop implements Runnable
{

    //game loop fields
    private Thread thread;
    private int FPS=30;
    private long targetTime = 1000/FPS;
    private boolean isRunning = false;

    public void start()
    {
        isRunning = true;
        thread = new Thread(this, "Game Loop");
        thread.start();
    }


    public void stop()
    {
        isRunning=false;
        try
        {
            thread.join();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public GameLoop()
    {

    }

    //game loop
    public void run()
    {
        long start, elapsed, wait;
        while(isRunning)
        {
            start = System.currentTimeMillis();

            update();
            elapsed = System.currentTimeMillis() - start;

            wait = targetTime - elapsed;
            if(wait<5)
            {
                wait =5;
            }
            try
            {
                Thread.sleep(wait);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        stop();
    }

    private int x=0;

    public void update()
    {
        x++;
    }

}
