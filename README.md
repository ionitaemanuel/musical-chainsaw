Acuma facem ceva legat de ceva design patterns pe care le folosim si noi, si intre timp pregatim putin terenul si ca sa invatam putin kafka si event driven dev :)


1. Start by creating a base DomainEvent interface. Next, create two concrete classes that implements it:
    a PaymentEvent (with fields like transaction ID, user ID, amount, and payment method) and a 
    NotificationEvent (with fields like recipient, message body, and channel type). Please add min 6 fields

2. Implement the Builder Pattern: Instead of using massive constructors, manually write a Builder pattern inside of these event classes
   ( You can do this only for PaymentEvent for now). Do not use Lombok's @Builder for this; we want you to write the inner static classes and method chaining by hand.

3. Create an EventProcessor interface with a process(DomainEvent event) method. Then, create separate implementations for processing 
   payments (PaymentEventProcessor) and notifications (NotificationEventProcessor). For now, these processors can simply log the event details to the console.

4. Write an EventProcessorFactory that takes an event type identifier and returns the correct processor implementation.

5. Create endpoints to accept these events (e.g., POST /events/payments and POST /events/notifications). 
   Your controller should use your Builders to construct the objects from the request payload, 
   and then use the Factory to find the right processor and execute it. 
   The controller itself should have zero knowledge of how the routing or processing actually works.
   You can use open api for these since it’s easier imo