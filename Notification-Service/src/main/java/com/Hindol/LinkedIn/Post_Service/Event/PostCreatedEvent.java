package com.Hindol.LinkedIn.Post_Service.Event;

import lombok.Data;

@Data
public class PostCreatedEvent {
    Long creatorId;
    String content;
    Long postId;
}
