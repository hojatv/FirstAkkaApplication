package akka.first.app.pingpong;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by hovaheb on 6/29/2016.
 */
public class PingPongApplication {
    public static void main(String[] args) throws Exception {
        ActorSystem _system = ActorSystem.create("PingPongApp");
        ActorRef pingPongActor = _system.actorOf(new
                        Props(PingPongActor.class),
                "pingPongActor");
        pingPongActor.tell("PING");

        Thread.sleep(5000);

        _system.shutdown();
    }
}
