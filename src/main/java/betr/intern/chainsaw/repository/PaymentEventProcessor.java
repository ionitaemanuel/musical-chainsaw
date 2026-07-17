package betr.intern.chainsaw.repository;

import betr.intern.chainsaw.model.domain.DomainEvent;
import betr.intern.chainsaw.model.domain.PaymentEvent;

public class PaymentEventProcessor implements EventProcessor {

    @Override
    public void process(DomainEvent event) {
        System.out.println("bruuh mocva");
        logger.info(event.toString());
    }

    @Override
    public Class<PaymentEvent> getSupportedEventClass() {
        return PaymentEvent.class;
    }
}
