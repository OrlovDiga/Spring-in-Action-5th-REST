package tacos.messaging;

import tacos.Order;

/**
 * @author Orlov Diga
 */
public interface OrderMessagingService {

    void sendOrder(Order order);

}
