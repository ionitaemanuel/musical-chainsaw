package betr.intern.chainsaw.factory;

import betr.intern.chainsaw.model.domain.DomainEvent;
import betr.intern.chainsaw.model.domain.EventType;
import betr.intern.chainsaw.repository.EventProcessor;
import betr.intern.chainsaw.repository.NotificationEventProcessor;
import betr.intern.chainsaw.repository.PaymentEventProcessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EventProcessorFactory {
    private static final Map<EventType, EventProcessor> processorMap = new HashMap<>();

    private EventProcessorFactory() {
        throw new AssertionError();
    }

    static {
        NotificationEventProcessor notificationProcessor = new NotificationEventProcessor();
        PaymentEventProcessor paymentProcessor = new PaymentEventProcessor();

        processorMap.put(EventType.NOTIFICATION, notificationProcessor);
        processorMap.put(EventType.PAYMENT, paymentProcessor);
    }

    public static <T extends DomainEvent> EventProcessor createEventProcessor(EventType type) {
        return Objects.requireNonNull(processorMap.get(type));
    }
}
