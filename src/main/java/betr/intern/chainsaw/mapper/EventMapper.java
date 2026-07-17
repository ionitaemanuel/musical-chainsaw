package betr.intern.chainsaw.mapper;

import betr.intern.chainsaw.generated.model.NotificationEventRequest;
import betr.intern.chainsaw.generated.model.PaymentEventRequest;
import betr.intern.chainsaw.model.domain.NotificationEvent;
import betr.intern.chainsaw.model.domain.PaymentEvent;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public PaymentEvent toEvent(PaymentEventRequest paymentEventRequest) {
        return new PaymentEvent.Builder(
                        paymentEventRequest.getTransactionId(),
                        paymentEventRequest.getUserId(),
                        paymentEventRequest.getAmount(),
                        paymentEventRequest.getPaymentMethod())
                .build();
    }

    public NotificationEvent toEvent(NotificationEventRequest notificationEventRequest) {
        return new NotificationEvent.Builder(
                        notificationEventRequest.getRecipient(),
                        notificationEventRequest.getMessageBody(),
                        notificationEventRequest.getChannelType())
                .build();
    }
}
