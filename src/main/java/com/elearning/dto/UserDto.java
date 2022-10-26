package com.elearning.dto;

import com.elearning.model.authentication.Role;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private Set<Role> roles;
    private Long isSupervisedBy;
}
