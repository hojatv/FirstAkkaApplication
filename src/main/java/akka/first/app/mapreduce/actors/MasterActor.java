package akka.first.app.mapreduce.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.first.app.mapreduce.messages.MapData;
import akka.first.app.mapreduce.messages.ReduceData;
import akka.first.app.mapreduce.messages.Result;
import akka.routing.RoundRobinRouter;

/**
 * Master actor is a Supervisor actor and responsible for the instantiation of the child actors. Master actor is the gateway for all messages that are passed on to the other
 * actors, namely the Map actor and Aggregate actor.
 */
public class MasterActor extends UntypedActor {
    ActorRef mapActor = getContext().actorOf(
            new Props(MapActor.class).withRouter(new
                    RoundRobinRouter(5)), "map");
    ActorRef reduceActor = getContext().actorOf(
            new Props(ReduceActor.class).withRouter(new
                    RoundRobinRouter(5)), "reduce");
    ActorRef aggregateActor = getContext().actorOf(
            new Props(AggregateActor.class), "aggregate");

    /**
     * The onReceive() method listens to the following kinds of messages:
     * • String messages that need to be passed to the Map actor
     * • MapData objects received from the Map actor that are passed to the Reduce actor
     * • ReduceData objects received from the Reduce actor that are passed to the Aggregate actor
     * • Result messages that need to be forwarded to the Aggregate actor for getting the result
     *
     * @param message
     * @throws Exception
     */
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            mapActor.tell(message, getSelf());
        } else if (message instanceof MapData) {
            reduceActor.tell(message, getSelf());
        } else if (message instanceof ReduceData) {
            aggregateActor.tell(message);
        } else if (message instanceof Result) {
            aggregateActor.forward(message, getContext());
        } else
            unhandled(message);
    }
}