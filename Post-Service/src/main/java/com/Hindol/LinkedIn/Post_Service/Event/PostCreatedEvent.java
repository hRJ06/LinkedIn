package com.Hindol.LinkedIn.Post_Service.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreatedEvent {
    Long creatorId;
    String content;
    Long postId;
}
