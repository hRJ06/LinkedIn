package com.Hindol.LinkedIn.Connection_Service.Service;

import com.Hindol.LinkedIn.Connection_Service.Entity.Person;

import java.util.List;

public interface ConnectionService {
    List<Person> getFirstDegreeConnections(Long userId);
}
