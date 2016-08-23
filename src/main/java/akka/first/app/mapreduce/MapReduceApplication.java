package akka.first.app.mapreduce;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.first.app.mapreduce.java.actors.MasterActor;
import akka.first.app.mapreduce.messages.Result;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;

public class MapReduceApplication {
    public static void main(String[] args) throws Exception {
        Timeout timeout = new Timeout(Duration.parse("5 seconds"));
        /*
         * ActorSystem is a container in which the actors are instantiated
         */
        ActorSystem _system = ActorSystem.create("MapReduceApp");
        ActorRef master = _system.actorOf(new
                        Props(MasterActor.class),
                "master");
        master.tell("The quick brown fox tried to jump over the lazy dog and fell on the dog");
        master.tell("Dog is man's best friend");
        master.tell("Dog and Fox belong to the same family");
        /*
         * We have Thread.sleep() because there is no guarantee in which order the messages are processed.
         * Thread.sleep() method ensures that all the string sentence messages are processed completely before we send the Result message.
         */
        Thread.sleep(5000);
        /*
         * we send the Result message to the Master actor to see the results
         */
        Future<Object> future = Patterns.ask(master, new Result(), timeout);
        String result = (String) Await.result(future,
                timeout.duration());
        System.out.println(result);
        _system.shutdown();
    }
}