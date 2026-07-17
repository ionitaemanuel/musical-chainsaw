package betr.intern.chainsaw.model.domain;

public class PaymentEvent implements DomainEvent {
    private final String transactionId;
    private final String userId;
    private final int amount;
    private final String paymentMethod;
    private final String robloxDefinition;
    private final String robuxAstounder;

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

        public PaymentEvent build() {
            return new PaymentEvent(this);
        }
    }

    private PaymentEvent(Builder builder) {
        transactionId = builder.transactionId;
        userId = builder.userId;
        amount = builder.amount;
        paymentMethod = builder.paymentMethod;
        robloxDefinition = builder.robloxDefinition;
        robuxAstounder = builder.robuxAstounder;
    }
}
