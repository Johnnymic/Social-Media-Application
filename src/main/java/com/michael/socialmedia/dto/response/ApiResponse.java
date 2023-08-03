package com.michael.socialmedia.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TimeZoneStorage;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T>{

    private String message;

    @TimeZoneStorage
    private LocalDateTime dateTime;

    private T data;

    public ApiResponse(T data) {
        this.message = "Processed successful";
        this.dateTime = LocalDateTime.now();
        this.data = data;
    }
}
