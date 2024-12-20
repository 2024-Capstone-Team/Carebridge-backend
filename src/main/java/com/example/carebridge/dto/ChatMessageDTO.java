package com.example.carebridge.dto;

import com.example.carebridge.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 클라이언트와 서버 간의 메시지 전송을 위한 DTO 클래스
 * 실제 엔티티와 구분하여 데이터 전송 시 사용됩니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private Integer messageId;
    private Integer senderId;
    private Integer receiverId;
    private String messageContent;
    private String timestamp;
    private Boolean readStatus;
    private Integer conversationId;

    /**
     * DTO를 Entity로 변환하는 메소드
     * @return Message 엔티티 객체
     */
    public Message toEntity() {
        Message message = new Message();
        message.setSenderId(this.senderId);
        message.setReceiverId(this.receiverId);
        message.setMessageContent(this.messageContent);
        message.setTimestamp(this.timestamp);
        message.setReadStatus(this.readStatus);
        message.setConversationId(this.conversationId);
        return message;
    }
}