package org.example.dao.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@Entity
@Table(name = "сlients_data_source_error_log")
public class DataSourceErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false)
    private Long id;

    @Column(columnDefinition = "TEXT")
    String exceptionStackTraceText;

    @Column(columnDefinition = "TEXT")
    String message;

    @Column(columnDefinition = "TEXT")
    String methodSignature;


}
