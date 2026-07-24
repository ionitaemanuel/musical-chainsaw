package betr.intern.chainsaw.repository;

import betr.intern.chainsaw.model.domain.NotificationEvent;

public interface NotificationEventProcessor extends EventProcessor {
    void process(NotificationEvent event);
}
