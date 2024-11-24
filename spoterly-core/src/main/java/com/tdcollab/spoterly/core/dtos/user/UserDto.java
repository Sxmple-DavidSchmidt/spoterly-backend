package com.tdcollab.spoterly.core.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdcollab.spoterly.core.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @JsonProperty("username")
    private String username;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private Role role;
}
