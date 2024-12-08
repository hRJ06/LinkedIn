package com.Hindol.LinkedIn.Connection_Service.Controller;

import com.Hindol.LinkedIn.Connection_Service.Entity.Person;
import com.Hindol.LinkedIn.Connection_Service.Service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionController {
    private final ConnectionService connectionService;
    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstConnections() {
        List<Person> firstConnections = connectionService.getFirstDegreeConnections();
        return ResponseEntity.ok(firstConnections);
    }
    @PostMapping("/request/{userId}")
    public ResponseEntity<Boolean> sendConnectionRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.sendConnectionRequest(userId));
    }
    @PostMapping("/accept/{userId}")
    public ResponseEntity<Boolean> acceptConnectionRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.acceptConnectionRequest(userId));
    }
    @PostMapping("/reject/{userId}")
    public ResponseEntity<Boolean> rejectConnectionRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.rejectConnectionRequest(userId));
    }
}
