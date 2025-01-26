package com.example.carebridge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "Examination_Schedule") // MySQL 의 Examination_Schedule 테이블과 매핑
public class ExaminationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 증가 설정
    private Integer id; // 검진 일정 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;  // Patient와의 연관 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_staff_id", nullable = false)
    private MedicalStaff medicalStaff;  // MedicalStaff와의 연관 관계

    @Column(name = "schedule_date", nullable = false) // 검진 일정 날짜 컬럼과 매핑
    private LocalDateTime scheduleDate; // 검진 날짜

    @Column(name = "details") // 검진 세부사항 컬럼과 매핑
    private String details; // 검진 세부사항

    @Column(name = "category", nullable = false) // 검진 종류 코드 컬럼과 매핑
    private String category; // 검진 종류 코드

}