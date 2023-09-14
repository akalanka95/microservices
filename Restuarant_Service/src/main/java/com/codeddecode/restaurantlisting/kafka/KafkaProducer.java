package com.codeddecode.restaurantlisting.kafka;

import com.codeddecode.restaurantlisting.dto.RestaurantDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    private KafkaTemplate<String,String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessages(String topic, List<RestaurantDTO> restaurantList) {
        // Iterate through the list of messages and send them individually
        for (RestaurantDTO restaurant : restaurantList) {
            String restaurantJson = serializeToJson(restaurant);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, "1", restaurantJson);

            // Send the individual message
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);

            // Handle the callback for each message
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    // Called when the message is successfully sent
                    RecordMetadata metadata = result.getRecordMetadata();
                    System.out.println("Message sent successfully to partition " + metadata.partition());
                }

                @Override
                public void onFailure(Throwable ex) {
                    // Called when the message sending fails
                    System.err.println("Error sending message: " + ex.getMessage());
                }
            });
        }
    }


private static String serializeToJson(RestaurantDTO restaurant) {
        // Implement JSON serialization logic here
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(restaurant);
            return json;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
