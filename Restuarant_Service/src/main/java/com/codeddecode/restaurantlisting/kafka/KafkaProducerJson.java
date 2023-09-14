package com.codeddecode.restaurantlisting.kafka;

import com.codeddecode.restaurantlisting.dto.RestaurantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerJson {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerJson.class);

    private KafkaTemplate<String , RestaurantDTO> kafkaTemplate;

    public KafkaProducerJson(KafkaTemplate<String, RestaurantDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(RestaurantDTO restaurantDTO){

        LOGGER.info("Data send %s",restaurantDTO.toString());

        Message<RestaurantDTO> message = MessageBuilder.withPayload(restaurantDTO)
                .setHeader(KafkaHeaders.TOPIC ,"topic-restaurantDetails")
                .build();

        kafkaTemplate.send(message);
    }
}
