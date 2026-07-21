package betr.intern.chainsaw.model.domain;

public record NotificationEvent(
        String recipient, String messageBody, String channelType, int sigmaCounter, int omegaCounter, int alphaCounter)
        implements DomainEvent {
    public static class Builder {
        private final String recipient;
        private final String messageBody;
        private final String channelType;
        private int sigmaCounter = 0;
        private int omegaCounter = 0;
        private int alphaCounter = 0;

        public Builder(String recipient, String messageBody, String channelType) {
            this.recipient = recipient;
            this.messageBody = messageBody;
            this.channelType = channelType;
        }

        public Builder sigmaCounter(int val) {
            sigmaCounter = val;
            return this;
        }

        public Builder omegaCounter(int val) {
            omegaCounter = val;
            return this;
        }

        public Builder alphaCounter(int val) {
            alphaCounter = val;
            return this;
        }

        public NotificationEvent build() {
            return new NotificationEvent(this);
        }
    }

    private NotificationEvent(Builder builder) {
        this(
                builder.recipient,
                builder.messageBody,
                builder.channelType,
                builder.sigmaCounter,
                builder.omegaCounter,
                builder.alphaCounter);
    }
}
