package akka.first.app.typedactors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.japi.Option;
import akka.util.Duration;
import akka.util.Timeout;

import static akka.actor.ActorSystem.create;

/*
 * The basic premise of the Typed Actor Model is communication via method calls.
 * The parameter values are the messages and these need to be immutable. The actor's
 * state responds or reacts based on the method that is invoked onto it
 */
public class TypedActorApplication {
    public static void main(String[] args) throws Exception {
        Timeout timeout = new Timeout(Duration.parse("5 seconds"));
        ActorSystem _system = create("TypedActorsExample");
        /*
         * The typed actor has been implemented as an Akka extension. So to get hold of the extension, the following call gets the extension object:
         */
        CalculatorInt calculator = TypedActor.get(_system)
                .typedActorOf(new TypedProps<Calculator>(
                        CalculatorInt.class, Calculator.class));
        /*
         *Fire and forget messages
         */
        calculator.incrementCount();
        /*
         *Send and receive messages
         */
        // Invoke the method and wait for result
        Future<Integer> future = calculator.add(Integer.valueOf(14),
                Integer.valueOf(6));
        Integer result = Await.result(future, timeout.duration());
        System.out.println("result: " + result);

        //Method invocation in a blocking way
        Option<Integer> counterResult = calculator.incrementAndReturn();
        System.out.println("counterResult: " + counterResult);

        //Get access to the ActorRef
        ActorRef calActor = TypedActor.get(_system)
                .getActorRefFor(calculator);
        //pass a message
        calActor.tell("Hi there");


        TypedActor.get(_system).stop(calculator);
        _system.shutdown();
    }
}
