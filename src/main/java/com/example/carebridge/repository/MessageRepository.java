package com.example.carebridge.repository;

import com.example.carebridge.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 메시지 데이터 접근을 위한 리포지토리 인터페이스
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * 읽지 않은 메시지 찾기
     * @param receiverId 수신자 ID
     * @return 읽지 않은 메시지 목록
     */
    List<Message> findByReceiverIdAndReadStatusFalse(Integer receiverId);

    /**
     * 대화 이력 조회
     * @param conversationId 대화 ID
     * @return 대화 메시지 목록
     */
    List<Message> findByConversationIdOrderByTimestampAsc(Integer conversationId);

    /**
     * 특정 사용자의 모든 대화 ID 찾기
     * @param userId 사용자 ID
     * @return 대화 ID 목록
     */
    @Query("SELECT DISTINCT m.conversationId FROM Message m " +
            "WHERE m.senderId = :userId OR m.receiverId = :userId")
    List<Integer> findConversationIdsByUserId(@Param("userId") Integer userId);

    /**
     * 읽지 않은 메시지 수 카운트
     * @param userId 사용자 ID
     * @return 읽지 않은 메시지 수
     */
    @Query("SELECT COUNT(m) FROM Message m " +
            "WHERE m.receiverId = :userId AND m.readStatus = false")
    Long countUnreadMessages(@Param("userId") Integer userId);
}