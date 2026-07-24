package betr.intern.chainsaw.repository;

import betr.intern.chainsaw.model.domain.NotificationEvent;

public class SmsNotificationEventProcessor implements NotificationEventProcessor {

    @Override
    public void process(NotificationEvent event) {
        System.out.println(event.toString());
    }
}
