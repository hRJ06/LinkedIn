package com.Hindol.LinkedIn.Connection_Service.Service;

import com.Hindol.LinkedIn.Connection_Service.Entity.Person;

import java.util.List;

public interface ConnectionService {
    List<Person> getFirstDegreeConnections();
    Boolean sendConnectionRequest(Long receiverId);
    Boolean acceptConnectionRequest(Long senderId);
    Boolean rejectConnectionRequest(Long senderId);
}
