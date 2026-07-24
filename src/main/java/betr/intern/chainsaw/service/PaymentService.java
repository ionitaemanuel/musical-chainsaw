package betr.intern.chainsaw.service;

import betr.intern.chainsaw.model.domain.PaymentEvent;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public void process(PaymentEvent event) {
        System.out.println(event.toString());
    }
}
