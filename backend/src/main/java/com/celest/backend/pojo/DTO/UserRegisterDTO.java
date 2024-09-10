package com.celest.backend.pojo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "用户注册的DTO",requiredProperties = {"username","password"})
public class UserRegisterDTO {
    @Schema(name = "用户名")
    private String username;
    @Schema(name = "用户密码")
    private String password;
}
