package betr.intern.chainsaw.repository;

import betr.intern.chainsaw.model.domain.DomainEvent;

public interface EventProcessor {
    void process(DomainEvent event);
}
