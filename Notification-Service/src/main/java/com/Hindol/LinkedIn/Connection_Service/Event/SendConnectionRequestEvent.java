package com.Hindol.LinkedIn.Connection_Service.Event;

import lombok.Data;

@Data
public class SendConnectionRequestEvent {
    private Long senderId;
    private Long receiverId;
}
