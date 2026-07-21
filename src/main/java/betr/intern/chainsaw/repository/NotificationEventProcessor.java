package betr.intern.chainsaw.repository;

import betr.intern.chainsaw.model.domain.DomainEvent;

public class NotificationEventProcessor implements EventProcessor {

    @Override
    public void process(DomainEvent event) {
        System.out.println(event.toString());
    }
}
