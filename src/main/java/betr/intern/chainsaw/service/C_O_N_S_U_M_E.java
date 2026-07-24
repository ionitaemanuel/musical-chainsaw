package betr.intern.chainsaw.service;

import betr.intern.chainsaw.factory.NotificationEventProcessorFactory;
import betr.intern.chainsaw.model.domain.NotificationEvent;
import betr.intern.chainsaw.repository.NotificationEventProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class C_O_N_S_U_M_E {

    @KafkaListener(topics = "notifications", groupId = "${spring.kafka.consumer.group-id}")
    public void process(NotificationEvent notificationEvent) {
        final NotificationEventProcessor notificationEventProcessor =
                NotificationEventProcessorFactory.createEventProcessor(notificationEvent.getChannelType());
        notificationEventProcessor.process(notificationEvent);
    }
}
