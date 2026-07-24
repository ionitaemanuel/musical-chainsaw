package betr.intern.chainsaw.controller.rest;

import betr.intern.chainsaw.generated.controller.rest.EventsApi;
import betr.intern.chainsaw.generated.model.NotificationEventRequest;
import betr.intern.chainsaw.generated.model.PaymentEventRequest;
import betr.intern.chainsaw.mapper.EventMapper;
import betr.intern.chainsaw.model.domain.NotificationEvent;
import betr.intern.chainsaw.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventRestController implements EventsApi {
    private final EventMapper eventMapper;
    private final PaymentService paymentService;
    private final KafkaTemplate<String, NotificationEvent> eventKafkaTemplate;

    public EventRestController(
            final EventMapper eventMapper,
            PaymentService paymentService,
            KafkaTemplate<String, NotificationEvent> eventKafkaTemplate) {
        this.eventMapper = eventMapper;
        this.paymentService = paymentService;
        this.eventKafkaTemplate = eventKafkaTemplate;
    }

    @Override
    public ResponseEntity<Void> processNotificationEvent(
            @RequestBody NotificationEventRequest notificationEventRequest) {
        NotificationEvent event = eventMapper.toEvent(notificationEventRequest);
        eventKafkaTemplate.send("notifications", event.getRecipient(), event);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> processPaymentEvent(PaymentEventRequest paymentEventRequest) {
        paymentService.process(eventMapper.toEvent(paymentEventRequest));
        return ResponseEntity.ok().build();
    }
}
