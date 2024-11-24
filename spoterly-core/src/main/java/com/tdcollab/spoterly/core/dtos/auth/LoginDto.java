package com.tdcollab.spoterly.core.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
