package tacos.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tacos.Order;

/**
 * @author Orlov Diga
 */
@Service
public class KafkaOrderMessagingServiceImpl implements OrderMessagingService {

    private KafkaTemplate<String, Order> kafka;

    @Autowired
    public KafkaOrderMessagingServiceImpl(KafkaTemplate<String, Order> kafka) {
        this.kafka = kafka;
    }

    @Override
    public void sendOrder(Order order) {
        kafka.sendDefault(order);
    }

 /*   @Override
    public void sendOrder(Order order) {
        kafka.send("tacocloud.orders.topic", order);
    }*/


}
