package com.liudi.nettychat.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author liuD
 * @Date 2022/6/21 2:38 下午
 * @PackageName:com.liudi.nettychat.dto
 * @ClassName: UserDto
 * @Description: UserDto
 * @Version 1.0
 */
@Data
public class UserDto {

    @NotBlank(message = "账号不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
