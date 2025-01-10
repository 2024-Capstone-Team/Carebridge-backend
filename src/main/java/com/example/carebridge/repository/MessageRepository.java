package com.example.carebridge.repository;

import com.example.carebridge.entity.Message;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findMessageContentByPatientId(Integer patientId);
    @Nonnull
    List<Message> findAll();
}


