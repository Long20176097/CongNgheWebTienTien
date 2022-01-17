package com.example.zalo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSummary {

    private Integer id;
    private String username;
    private String name;
    private String profilePicture;
}
