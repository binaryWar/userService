package com.practice.authorization.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Entity
public class Session extends BaseModel{
    private String token;
    private String ipAddress;

    @ManyToOne
    private User user;

    private Date exp_at;
    private String deviceInfo;

}
