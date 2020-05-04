package tacos.kitchen.messaging.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import tacos.Order;

/**
 * @author Orlov Diga
 */
@Component
public class RabbitOrderReceiver {

    private RabbitTemplate rabbit;
    private MessageConverter converter;

    @Autowired
    public RabbitOrderReceiver(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
        this.converter = rabbit.getMessageConverter();
    }

    public Order receiveOrder() {
        Message message = rabbit.receive("tacocloud.orders");

        return message != null ? (Order) converter.fromMessage(message) : null;
    }

    public Order receiveOrderWithTimeout() {
        Message message = rabbit.receive("tacocloud.orders", 30000);

        return message != null ? (Order) converter.fromMessage(message) : null;
    }

    public Order receiveOrderWithConverter() {
        return (Order) rabbit.receiveAndConvert("tacocloud.orders");
    }

    public Order receiveTypeSafeOrder() {
        return rabbit.receiveAndConvert("tacocloud.orders", new ParameterizedTypeReference<Order>() {});
    }




}
