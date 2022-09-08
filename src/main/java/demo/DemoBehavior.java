package demo;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;
import akka.persistence.typed.PersistenceId;
import akka.persistence.typed.javadsl.CommandHandler;
import akka.persistence.typed.javadsl.EventHandler;
import akka.persistence.typed.javadsl.EventSourcedBehavior;
import demo.DemoBehavior.DemoCommand;
import demo.DemoBehavior.DemoEvent;
import demo.DemoBehavior.DemoState;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


public class DemoBehavior extends EventSourcedBehavior<DemoCommand, DemoEvent, DemoState> {
    interface DemoCommand extends Jsonable {}

    static class Ping implements DemoCommand {
        final ActorRef<Pong> replyTo;

        Ping(ActorRef<Pong> replyTo) {
            this.replyTo = replyTo;
        }
    }

    static class Pong implements Jsonable {}

    interface DemoEvent extends Jsonable {}

    static class Pinged implements DemoEvent {
        OffsetDateTime when;
    }

    static class DemoState implements Jsonable {
        List<Pinged> events = new ArrayList<>();

        DemoState onPinged(Pinged event) {
            var newState = new DemoState();
            newState.events.addAll(events);
            newState.events.add(event);

            return newState;
        }
    }

    static Behavior<DemoCommand> create() {
        return Behaviors.setup(context -> new DemoBehavior(PersistenceId.ofUniqueId("anId")));
    }

    public DemoBehavior(PersistenceId persistenceId) {
        super(persistenceId);
    }

    @Override
    public DemoState emptyState() {
        return new DemoState();
    }

    @Override
    public CommandHandler<DemoCommand, DemoEvent, DemoState> commandHandler() {
        return newCommandHandlerBuilder()
                .forAnyState()
                .onCommand(Ping.class, command -> Effect()
                        .persist(new Pinged())
                        .thenReply(command.replyTo, state -> new Pong()))
                .build();
    }

    @Override
    public EventHandler<DemoState, DemoEvent> eventHandler() {
        return newEventHandlerBuilder()
                .forAnyState()
                .onEvent(Pinged.class, DemoState::onPinged)
                .build();
    }
}
