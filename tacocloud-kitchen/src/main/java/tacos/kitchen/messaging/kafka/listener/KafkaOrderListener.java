package tacos.kitchen.messaging.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.converter.KafkaMessageHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import tacos.Order;
import tacos.kitchen.KitchenUI;

/**
 * @author Orlov Diga
 */
@Slf4j
@Component
public class KafkaOrderListener {

    private KitchenUI ui;

    @Autowired
    public KafkaOrderListener(KitchenUI ui) {
        this.ui = ui;
    }

  /*  @KafkaListener(topics = "tacocloud.orders.topic")
    public void handle(Order order) {
        ui.displayOrder(order);
    }*/

    @KafkaListener(topics = "tacocloud.orders.topic")
    public void handle(Order order, ConsumerRecord<String, Order> record) {
        log.info("Received from partition {} with timestamp {}",
                record.partition(),
                record.timestamp());

        ui.displayOrder(order);
    }

    @KafkaListener(topics = "tacocloud.orders.topic")
    public void handle(Order order, Message<Order> message) {
        MessageHeaders headers = message.getHeaders();

        log.info("Received from partition {} with timestamp {}",
                headers.get(KafkaHeaders.RECEIVED_PARTITION_ID),
                headers.get(KafkaHeaders.TIMESTAMP));

        ui.displayOrder(order);
    }
}
