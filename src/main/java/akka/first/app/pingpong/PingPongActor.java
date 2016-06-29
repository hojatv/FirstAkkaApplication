package akka.first.app.pingpong;

import akka.actor.UntypedActor;
import akka.japi.Procedure;

/**
 * Created by hovaheb on 6/29/2016.
 */
public class PingPongActor extends UntypedActor {
    static String PING = "PING";
    static String PONG = "PONG";
    int count = 0;

    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            if (((String) message).matches(PING)) {
                System.out.println("PING");
                count += 1;
                Thread.sleep(100);
                getSelf().tell(PONG);
                /*
                 *Call the become() method where we swap the message loop for handling the PONG message
                 */
                getContext().become(new Procedure<Object>() {
                    public void apply(Object message) {
                        if (message instanceof String) {
                            if (((String) message).
                                    matches(PONG)) {
                                System.out.println("PONG");
                                count += 1;
                                try {
                                    Thread.sleep(100);
                                } catch
                                        (Exception e) {
                                }
                                getSelf().tell(PING);
                                /*
                                 * Restores the original message handling loop for PING messages
                                 */
                                getContext().unbecome();
                            }
                        }
                    }
                });
                if (count > 10)
                    getContext().stop(getSelf());
            }
        }
    }
}