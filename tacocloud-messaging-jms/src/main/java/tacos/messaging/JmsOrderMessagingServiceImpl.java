package tacos.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import tacos.Order;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author Orlov Diga
 */
@Service
public class JmsOrderMessagingServiceImpl implements OrderMessagingService {

    private JmsTemplate jms;
    private Destination orderQueue;

    @Autowired
    public JmsOrderMessagingServiceImpl(JmsTemplate jms, Destination orderQueue) {
        this.jms = jms;
        this.orderQueue = orderQueue;
    }

    @Override
    public void sendOrder(Order order) {
        jms.convertAndSend("tacocloud.order.queue", order, this::addOrderSource);
    }

   /* @Override
    public void sendOrder(Order order) {
        jms.send(orderQueue, session -> session.createObjectMessage(order));
    }*/

  /*  @Override
    public void sendOrder(Order order) {
        jms.send("tacocloud.order.queue", session -> session.createObjectMessage(order));
    }*/

    private Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }


}
