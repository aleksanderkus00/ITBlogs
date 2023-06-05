package com.ITBlogs.models;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterModel {
   private String username;
   private String email;
   private String password;
   private Set<String> role;
}
