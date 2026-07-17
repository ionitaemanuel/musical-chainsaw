package betr.intern.chainsaw.repository;

import betr.intern.chainsaw.model.domain.DomainEvent;

public class PaymentEventProcessor implements EventProcessor {

    @Override
    public void process(DomainEvent event) {
        System.out.println("bruuh mocva");
        logger.info(event.toString());
    }

}
