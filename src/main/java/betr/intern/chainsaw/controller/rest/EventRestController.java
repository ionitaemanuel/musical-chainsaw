package betr.intern.chainsaw.controller.rest;

import betr.intern.chainsaw.factory.EventProcessorFactory;
import betr.intern.chainsaw.generated.controller.rest.EventsApi;
import betr.intern.chainsaw.generated.model.NotificationEventRequest;
import betr.intern.chainsaw.generated.model.PaymentEventRequest;
import betr.intern.chainsaw.mapper.EventMapper;
import betr.intern.chainsaw.model.domain.NotificationEvent;
import betr.intern.chainsaw.model.domain.PaymentEvent;
import betr.intern.chainsaw.repository.EventProcessor;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

@RestController
public class EventRestController implements EventsApi {
    private final EventMapper eventMapper;

    public EventRestController(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return EventsApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> processNotificationEvent(NotificationEventRequest notificationEventRequest) {
        EventProcessor asd = EventProcessorFactory.createEventProcessor(NotificationEvent.class);
        asd.process(eventMapper.toEvent(notificationEventRequest));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> processPaymentEvent(PaymentEventRequest paymentEventRequest) {
        EventProcessor asd = EventProcessorFactory.createEventProcessor(PaymentEvent.class);
        asd.process(eventMapper.toEvent(paymentEventRequest));
        return ResponseEntity.ok().build();
    }
}
