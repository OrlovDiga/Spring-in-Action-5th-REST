package tacos.kitchen.messaging.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

import tacos.Order;
import tacos.kitchen.OrderReceiver;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * @author Orlov Diga
 */
public class JmsOrderReceiver implements OrderReceiver {

    private JmsTemplate jms;
    private MessageConverter converter;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter) {
        this.jms = jms;
        this.converter = converter;
    }

    @Override
    public Order receiveOrder() {
        return (Order) jms.receiveAndConvert("tacocloud.order.queue");
    }

   /* @Override
    public Order receiveOrder() throws JMSException {
        Message message = jms.receive("tacocloud.order.queue");

        return (Order) converter.fromMessage(message);
    }*/




}
