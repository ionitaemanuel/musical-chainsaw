package betr.intern.chainsaw.repository;

import betr.intern.chainsaw.model.domain.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface EventProcessor {
    static final Logger logger = LoggerFactory.getLogger(EventProcessor.class);

    void process(DomainEvent event);

}
