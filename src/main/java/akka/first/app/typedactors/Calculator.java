package akka.first.app.typedactors;

import akka.actor.ActorRef;
import akka.actor.TypedActor;
import akka.dispatch.Future;
import akka.dispatch.Futures;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Option;

/**
 * Created by hovaheb on 6/29/2016.
 */
public class Calculator implements CalculatorInt, TypedActor.PreStart, TypedActor.PostStop, TypedActor.Receiver {
    Integer counter = 0;
    LoggingAdapter log = Logging.getLogger(
            TypedActor.context().system(), this);

    //Non-blocking request response
    public Future<Integer> add(Integer first, Integer second) {
        return Futures.successful(first + second,
                TypedActor.dispatcher());
    }

    //Non-blocking request response
    public Future<Integer> subtract(Integer first,
                                    Integer second) {
        return Futures.successful(first - second,
                TypedActor.dispatcher());
    }

    //fire and forget
    public void incrementCount() {
        counter++;
    }

    //Blocking request response
    public Option<Integer> incrementAndReturn() {
        return Option.some(++counter);
    }

    //Allows to tap into the Actor PreStart hook
    public void preStart() {
        log.info("Actor Started !");
    }

    //Allows to tap into the Actor PostStop hook
    public void postStop() {
        log.info("Actor Stopped !");
    }

    public void onReceive(Object msg, ActorRef actor) {
        log.info("Received Message -> {}", msg);
    }
}