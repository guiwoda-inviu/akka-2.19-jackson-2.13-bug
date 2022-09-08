package demo;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.persistence.testkit.javadsl.EventSourcedBehaviorTestKit;
import demo.DemoBehavior.DemoCommand;
import demo.DemoBehavior.DemoEvent;
import demo.DemoBehavior.DemoState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActorSerializationTest {
    private static final ActorTestKit testKit = ActorTestKit.create(EventSourcedBehaviorTestKit.config());
    private static final EventSourcedBehaviorTestKit<DemoCommand, DemoEvent, DemoState> persistenceTestKit = EventSourcedBehaviorTestKit.create(
            testKit.system(), DemoBehavior.create());

    @Test
    void asd() {
        Assertions.assertTrue(true);
    }
}
