package betr.intern.chainsaw.event;

import betr.intern.chainsaw.model.ViewRecord;
import java.util.Map;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class UserViewsUpdatedEvent extends ApplicationEvent {
    private final Map<UUID, ViewRecord> userViews;

    public UserViewsUpdatedEvent(final Object source, final Map<UUID, ViewRecord> userViews) {
        super(source);
        this.userViews = Map.copyOf(userViews);
    }

    public Map<UUID, ViewRecord> getUserViews() {
        return userViews;
    }
}
