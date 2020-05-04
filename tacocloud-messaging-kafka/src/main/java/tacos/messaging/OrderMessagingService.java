package tacos.messaging;

import tacos.Order;

/**
 * @author Orlov Diga
 */
public interface OrderMessagingService {

    public void sendOrder(Order order);
}
