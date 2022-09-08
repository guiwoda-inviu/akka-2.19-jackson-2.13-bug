package demo;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;
import akka.persistence.typed.PersistenceId;
import akka.persistence.typed.javadsl.CommandHandler;
import akka.persistence.typed.javadsl.EventHandler;
import akka.persistence.typed.javadsl.EventSourcedBehavior;
import demo.DemoBehavior.DemoCommand;
import demo.DemoBehavior.DemoEvent;
import demo.DemoBehavior.DemoState;


public class DemoBehavior extends EventSourcedBehavior<DemoCommand, DemoEvent, DemoState> {
    interface DemoCommand {}

    interface DemoEvent {}
    static class DemoState {}

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
        return newCommandHandlerBuilder().build();
    }

    @Override
    public EventHandler<DemoState, DemoEvent> eventHandler() {
        return newEventHandlerBuilder().build();
    }
}
