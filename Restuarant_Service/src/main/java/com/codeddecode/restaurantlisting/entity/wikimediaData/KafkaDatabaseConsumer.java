package com.codeddecode.restaurantlisting.entity.wikimediaData;

import com.codeddecode.restaurantlisting.entity.websocket.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    @Autowired
    SimpMessagingTemplate template;

    private WikimediaDataRepository dataRepository;

    public KafkaDatabaseConsumer(WikimediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @KafkaListener(topics = "wikimedia_recentchange",groupId = "myGroup")
    public void consume(String eventMessage){

        LOGGER.info(String.format("Event message received -> %s", eventMessage));

        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);
        template.convertAndSend("/topic/user", eventMessage);

        dataRepository.save(wikimediaData);
    }

//    @KafkaListener(topics = "stock-feed", groupId = "my-group")
//    public void consume(ConsumerRecord<String, StockData> record) {
//        StockData stockData = record.value();
//        String stockId = stockData.getStockID();
//
//        // Check if there are subscribers for this stockID and send the data accordingly
//        // You can use a map or a database to store subscriber information
//
//        // Example: Send data to subscribers
//        // sendToSubscribers(stockData, stockId);
//    }
//
//    // Implement the logic to send data to subscribers based on stockID
//    // private void sendToSubscribers(StockData stockData, String stockId) {
//    //     ...
//    // }

}
