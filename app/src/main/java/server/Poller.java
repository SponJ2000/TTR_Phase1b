package server;

import communication.Result;

/**
 * Created by hao on 10/5/18.
 */

public class Poller {

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ServerProxy serverProxy = new ServerProxy();
            Result result = serverProxy.CheckUpdates();

            if (result.isSuccess()) {
                //do something in the success

            }
            else {
                //report an error
            }
        }
    }
}
