package com.Hindol.LinkedIn.Post_Service.Client;

import com.Hindol.LinkedIn.Post_Service.DTO.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "Connection-Service", path = "/connections")
public interface ConnectionClient {
    @GetMapping("/core/first-degree")
    List<PersonDTO> getFirstConnections();
}
