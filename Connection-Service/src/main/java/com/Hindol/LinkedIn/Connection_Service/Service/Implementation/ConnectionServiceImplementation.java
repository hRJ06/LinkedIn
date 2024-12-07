package com.Hindol.LinkedIn.Connection_Service.Service.Implementation;

import com.Hindol.LinkedIn.Connection_Service.Auth.UserContextHolder;
import com.Hindol.LinkedIn.Connection_Service.Entity.Person;
import com.Hindol.LinkedIn.Connection_Service.Repository.PersonRepository;
import com.Hindol.LinkedIn.Connection_Service.Service.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionServiceImplementation implements ConnectionService {
    private final PersonRepository personRepository;

    @Override
    public List<Person> getFirstDegreeConnections() {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting 1st degree connections of User with ID : {}", userId);
        return personRepository.getFirstDegreeConnections(userId);
    }
}
