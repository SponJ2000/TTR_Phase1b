package server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import communication.Result;

/**
 * Created by hao on 10/5/18.
 */

public class Poller {

    private static boolean running = false;
    private static ScheduledExecutorService scheduledExecutorService;
    private static ScheduledFuture scheduledFuture;

    private static final Runnable CheckUpdates = new Runnable() {
        @Override
        public void run() {
            if (running) {
                ServerProxy serverProxy = new ServerProxy();
                Result result = serverProxy.CheckUpdates();

                if (result.isSuccess()) {
                    //do something in the success

                } else {
                    //report an error
                }
            }
            else {
                scheduledExecutorService.shutdown();
            }
        }
    };

    public static void StartPoller () {
        running = true;
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(CheckUpdates,2,5, TimeUnit.SECONDS);
    }

    public static void EndPoller () {
        running = false;
    }

}
