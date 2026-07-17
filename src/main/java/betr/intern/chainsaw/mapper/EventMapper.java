package betr.intern.chainsaw.mapper;

import betr.intern.chainsaw.generated.model.NotificationEventRequest;
import betr.intern.chainsaw.generated.model.PaymentEventRequest;
import betr.intern.chainsaw.model.domain.NotificationEvent;
import betr.intern.chainsaw.model.domain.PaymentEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    PaymentEvent toEvent(PaymentEventRequest paymentEventRequest);

    NotificationEvent toEvent(NotificationEventRequest notificationEventRequest);
}
