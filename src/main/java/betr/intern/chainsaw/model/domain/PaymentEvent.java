package betr.intern.chainsaw.model.domain;

public record PaymentEvent(
        String transactionId,
        String userId,
        int amount,
        String paymentMethod,
        String robloxDefinition,
        String robuxAstounder)
        implements DomainEvent {

    public static class Builder {
        private final String transactionId;
        private final String userId;
        private final int amount;
        private final String paymentMethod;

        private String robloxDefinition;
        private String robuxAstounder;

        public Builder(String transactionId, String userId, int amount, String paymentMethod) {
            this.transactionId = transactionId;
            this.userId = userId;
            this.amount = amount;
            this.paymentMethod = paymentMethod;
        }

        public Builder robloxDefinition(String robloxDefinition) {
            this.robloxDefinition = robloxDefinition;
            return this;
        }

        public Builder robuxAstounder(String robuxAstounder) {
            this.robuxAstounder = robuxAstounder;
            return this;
        }
    }

    public static PaymentEvent builder(Builder builder) {
        return new PaymentEvent(builder);
    }

    private PaymentEvent(Builder builder) {
        this(
                builder.transactionId,
                builder.userId,
                builder.amount,
                builder.paymentMethod,
                builder.robloxDefinition,
                builder.robuxAstounder);
    }
}
