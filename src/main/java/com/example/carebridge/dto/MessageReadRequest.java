package com.example.carebridge.dto;

import java.util.List;

/**
 * 메시지 읽음 처리 요청을 위한 DTO 클래스
 */
public class MessageReadRequest {
    private List<Integer> messageIds; // 읽음 처리할 메시지 ID 목록
    private Integer userId;           // 요청한 사용자 ID

    // 기본 생성자
    public MessageReadRequest() {}

    /**
     * 모든 필드를 포함하는 생성자
     * @param messageIds 읽음 처리할 메시지 ID 목록
     * @param userId 사용자 ID
     */
    public MessageReadRequest(List<Integer> messageIds, Integer userId) {
        this.messageIds = messageIds;
        this.userId = userId;
    }

    // Getter와 Setter
    public List<Integer> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(List<Integer> messageIds) {
        this.messageIds = messageIds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}