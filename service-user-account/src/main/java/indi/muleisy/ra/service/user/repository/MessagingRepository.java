package indi.muleisy.ra.service.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MessagingRepository {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MessagingRepository(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSms(String phone, String code) {
        kafkaTemplate.send("sms-topic", phone + ":" + code);
    }

    public void sendEmail(String email, String code) {
        kafkaTemplate.send("email-topic", email + ":" + code);
    }
}
