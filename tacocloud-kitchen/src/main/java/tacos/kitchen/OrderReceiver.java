package tacos.kitchen;

import tacos.Order;

import javax.jms.JMSException;

/**
 * @author Orlov Diga
 */
public interface OrderReceiver {

    Order receiveOrder() throws JMSException;
}
