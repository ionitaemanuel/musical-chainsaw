package betr.intern.chainsaw.factory;

import betr.intern.chainsaw.model.domain.DomainEvent;
import betr.intern.chainsaw.repository.EventProcessor;
import betr.intern.chainsaw.repository.NotificationEventProcessor;
import betr.intern.chainsaw.repository.PaymentEventProcessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EventProcessorFactory {
    private static final Map<Class<? extends DomainEvent>, EventProcessor> processorMap = new HashMap<>();

    private EventProcessorFactory() {
        throw new AssertionError();
    }

    static {
        NotificationEventProcessor notificationProcessor = new NotificationEventProcessor();
        PaymentEventProcessor paymentProcessor = new PaymentEventProcessor();

        processorMap.put(notificationProcessor.getSupportedEventClass(), notificationProcessor);
        processorMap.put(paymentProcessor.getSupportedEventClass(), paymentProcessor);
    }

    public static <T extends DomainEvent> EventProcessor createEventProcessor(Class<T> eventClass) {
        return Objects.requireNonNull(processorMap.get(eventClass));
    }
}
