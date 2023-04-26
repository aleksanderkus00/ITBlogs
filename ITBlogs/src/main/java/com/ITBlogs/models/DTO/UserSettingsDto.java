package com.ITBlogs.models.DTO;

import lombok.Data;

@Data
public class UserSettingsDto {
    private Long id;
    private String username;
    private String email;
    private String password;
}
