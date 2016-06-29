package akka.first.app.typedactors;

import akka.actor.TypedActor;
import akka.dispatch.Future;
import akka.dispatch.Futures;
import akka.japi.Option;

/**
 * Created by hovaheb on 6/29/2016.
 */
public class Calculator implements CalculatorInt {
    Integer counter = 0;

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
}