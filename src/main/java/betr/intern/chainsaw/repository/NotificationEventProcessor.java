package betr.intern.chainsaw.repository;

import betr.intern.chainsaw.model.domain.DomainEvent;
import betr.intern.chainsaw.model.domain.NotificationEvent;

public class NotificationEventProcessor implements EventProcessor {

    @Override
    public void process(DomainEvent event) {
        System.out.println("uuh sigma");
        logger.info(event.toString());
    }

    @Override
    public Class<NotificationEvent> getSupportedEventClass() {
        return NotificationEvent.class;
    }
}
