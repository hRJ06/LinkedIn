package com.Hindol.LinkedIn.Connection_Service.Controller;

import com.Hindol.LinkedIn.Connection_Service.Entity.Person;
import com.Hindol.LinkedIn.Connection_Service.Service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionController {
    private final ConnectionService connectionService;
    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>>  getFirstConnections(@PathVariable Long userId) {
        List<Person> firstConnections = connectionService.getFirstDegreeConnections(userId);
        return ResponseEntity.ok(firstConnections);
    }
}