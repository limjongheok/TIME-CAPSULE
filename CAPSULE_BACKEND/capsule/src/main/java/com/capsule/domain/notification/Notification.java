package com.capsule.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Notification {

    private long capsule_id;
    private String title;
    private String date;
    private String latitude;
    private String longitude;
}
