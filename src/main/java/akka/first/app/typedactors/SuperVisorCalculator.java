package akka.first.app.typedactors;

import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.TypedActor;
import akka.japi.Function;
import akka.util.Duration;

/**
 * Created by hovaheb on 6/29/2016.
 */
public class SupervisorCalculator implements TypedActor.Supervisor {
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.parse("10 second"), new Function<Throwable, SupervisorStrategy.Directive>() {
        public SupervisorStrategy.Directive apply(Throwable t) {
            if (t instanceof ArithmeticException) {
                return SupervisorStrategy.resume();
            } else if (t instanceof IllegalArgumentException) {
                return SupervisorStrategy.restart();
            } else if (t instanceof NullPointerException) {
                return SupervisorStrategy.stop();
            } else {
                return SupervisorStrategy.escalate();
            }
        }
    });
}