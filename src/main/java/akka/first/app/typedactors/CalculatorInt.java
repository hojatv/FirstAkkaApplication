package akka.first.app.typedactors;

import akka.dispatch.Future;
import akka.japi.Option;

/**
 * Created by hovaheb on 6/29/2016.
 */
public interface CalculatorInt {
    /*
     * Methods with the Future return type are dispatched in a request-reply manner similar
     * to the ask() method for untyped actors. There calls are non-blocking.
     */
    public Future<Integer> add(Integer first, Integer second);

    public Future<Integer> subtract(Integer first,
                                    Integer second);

    /*
     * Methods with the void return type are dispatched in a fire and forget manner similar to tell() for untyped actors
     */
    public void incrementCount();

    /*
     * Methods with the Option return type are also dispatched in a request-reply manner, but these calls are blocking.
     */
    public Option<Integer> incrementAndReturn();
}