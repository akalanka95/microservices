package com.codeddecode.restaurantlisting.kafka;

import com.codeddecode.restaurantlisting.dto.RestaurantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "topic-restaurantDetails" , groupId = "myGroup")
    public void consumer(String string){
        LOGGER.info(String.format("Message Received -> %s" ,string));
    }
}
