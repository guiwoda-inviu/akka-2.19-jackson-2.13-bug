package demo;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.persistence.testkit.javadsl.EventSourcedBehaviorTestKit;
import com.typesafe.config.ConfigFactory;
import demo.DemoBehavior.DemoCommand;
import demo.DemoBehavior.DemoEvent;
import demo.DemoBehavior.DemoState;
import demo.DemoBehavior.Ping;
import demo.DemoBehavior.Pinged;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActorSerializationTest {
    private static final ActorTestKit testKit = ActorTestKit.create(EventSourcedBehaviorTestKit.config()
            .withFallback(ConfigFactory.load("application-test.conf")));
    private static final EventSourcedBehaviorTestKit<DemoCommand, DemoEvent, DemoState> persistenceTestKit = EventSourcedBehaviorTestKit.create(
            testKit.system(), DemoBehavior.create());

    @Test
    void asd() {
        var result = persistenceTestKit.runCommand(Ping::new);
        Assertions.assertNotNull(result.reply());
        Assertions.assertNotNull(result.eventOfType(Pinged.class));
    }
}
