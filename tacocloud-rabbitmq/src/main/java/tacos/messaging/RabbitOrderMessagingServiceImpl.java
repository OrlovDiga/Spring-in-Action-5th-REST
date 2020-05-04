package tacos.messaging;


import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacos.Order;

/**
 * @author Orlov Diga
 */
@Service
public class RabbitOrderMessagingServiceImpl implements OrderMessagingService {

    private RabbitTemplate rabbit;

    @Autowired
    public RabbitOrderMessagingServiceImpl(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @Override
    public void sendOrder(Order order) {
        rabbit.convertAndSend("tacocloud.order", order, message -> {
            MessageProperties properties = message.getMessageProperties();

            properties.setHeader("X_ORDER_SOURCE", "WEB");

            return message;
        });
    }

 /*   @Override
    public void sendOrder(Order order) {
        rabbit.convertAndSend("tacocloud.order", order);
    }*/

    /*@Override
    public void sendOrder(Order order) {
        MessageConverter converter = rabbit.getMessageConverter();

        MessageProperties properties = new MessageProperties();

        Message message = converter.toMessage(order, properties);

        rabbit.send("tacocloud.order", message);
    }*/
}
