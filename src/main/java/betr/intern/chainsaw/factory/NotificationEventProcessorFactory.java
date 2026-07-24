package betr.intern.chainsaw.factory;

import betr.intern.chainsaw.model.domain.ChannelType;
import betr.intern.chainsaw.repository.EmailNotificationEventProcessor;
import betr.intern.chainsaw.repository.NotificationEventProcessor;
import betr.intern.chainsaw.repository.SmsNotificationEventProcessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NotificationEventProcessorFactory {
    private static final Map<ChannelType, NotificationEventProcessor> processorMap = new HashMap<>();

    private NotificationEventProcessorFactory() {
        throw new AssertionError();
    }

    static {
        EmailNotificationEventProcessor emailProcessor = new EmailNotificationEventProcessor();
        SmsNotificationEventProcessor smsProcessor = new SmsNotificationEventProcessor();

        processorMap.put(ChannelType.EMAIL, emailProcessor);
        processorMap.put(ChannelType.SMS, smsProcessor);
    }

    public static NotificationEventProcessor createEventProcessor(ChannelType type) {
        return Objects.requireNonNull(processorMap.get(type));
    }
}
