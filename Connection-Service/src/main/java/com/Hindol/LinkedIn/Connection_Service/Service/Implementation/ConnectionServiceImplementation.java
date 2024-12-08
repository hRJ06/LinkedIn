package com.Hindol.LinkedIn.Connection_Service.Service.Implementation;

import com.Hindol.LinkedIn.Connection_Service.Auth.UserContextHolder;
import com.Hindol.LinkedIn.Connection_Service.Entity.Person;
import com.Hindol.LinkedIn.Connection_Service.Event.AcceptConnectionRequestEvent;
import com.Hindol.LinkedIn.Connection_Service.Event.SendConnectionRequestEvent;
import com.Hindol.LinkedIn.Connection_Service.Repository.PersonRepository;
import com.Hindol.LinkedIn.Connection_Service.Service.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionServiceImplementation implements ConnectionService {
    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, SendConnectionRequestEvent> sendRequestKafkaTemplate;
    private final KafkaTemplate<Long, AcceptConnectionRequestEvent> acceptRequestKafkaTemplate;

    @Override
    public List<Person> getFirstDegreeConnections() {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting 1st degree connections of User with ID : {}", userId);
        return personRepository.getFirstDegreeConnections(userId);
    }

    @Override
    public Boolean sendConnectionRequest(Long receiverId) {
        Long senderId = UserContextHolder.getCurrentUserId();
        log.info("Trying to send connection request, Sender : {}, Receiver : {}", senderId, receiverId);
        if(senderId.equals(receiverId)) {
            throw new RuntimeException("Both sender and receiver are the same");
        }
        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if(alreadySentRequest) {
            throw new RuntimeException("Connection request already exists, cannot send again");
        }
        boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);
        if(alreadyConnected) {
            throw new RuntimeException("Already connected, cannot add connection request");
        }
        personRepository.addConnectionRequest(senderId, receiverId);
        SendConnectionRequestEvent sendConnectionRequestEvent = SendConnectionRequestEvent.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        sendRequestKafkaTemplate.send("send-connection-request-topic", sendConnectionRequestEvent);
        log.info("Successfully sent connection request");
        return true;
    }

    @Override
    public Boolean acceptConnectionRequest(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();
        boolean connectionRequestExists = personRepository.alreadyConnected(senderId, receiverId);
        if(!connectionRequestExists) {
            throw new RuntimeException("No connection request exists to accept");
        }
        personRepository.acceptConnectionRequest(senderId, receiverId);
        AcceptConnectionRequestEvent acceptConnectionRequestEvent = AcceptConnectionRequestEvent.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        acceptRequestKafkaTemplate.send("accept-connection-request-topic", acceptConnectionRequestEvent);
        log.info("Successfully accepted connection request, Sender - {}, Receiver - {}", senderId, receiverId);
        return true;
    }

    @Override
    public Boolean rejectConnectionRequest(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();
        boolean connectionRequestExists = personRepository.alreadyConnected(senderId, receiverId);
        if(!connectionRequestExists) {
            throw new RuntimeException("No connection request exists to reject");
        }
        personRepository.rejectConnectionRequest(senderId, receiverId);
        return true;
    }
}
