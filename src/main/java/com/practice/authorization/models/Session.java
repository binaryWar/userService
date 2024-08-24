package com.practice.authorization.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Entity
@JsonDeserialize(as = Session.class)
public class Session extends BaseModel{
    private String token;
    private String ipAddress;
    private String status;
    @ManyToOne
    private User user;

    private Date exp_at;
    private String deviceInfo;

}
