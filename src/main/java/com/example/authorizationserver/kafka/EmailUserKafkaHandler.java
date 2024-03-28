package com.example.authorizationserver.kafka;

import com.example.authorizationserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmailUserKafkaHandler {

    private final UserRepository userRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "sendIdForGetEmail", groupId = "auth_groupId")
    public void listen(String userIdStr) {
        userRepository.findById(UUID.fromString(userIdStr))
                .ifPresent(user -> kafkaTemplate.send("emailOfUser", user.getEmail()));
    }

}
