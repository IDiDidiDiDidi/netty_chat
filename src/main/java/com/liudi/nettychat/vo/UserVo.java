package com.liudi.nettychat.vo;

import lombok.Data;

/**
 * @Author liuD
 * @Date 2022/6/21 3:16 下午
 * @PackageName:com.liudi.nettychat.vo
 * @ClassName: UserVo
 * @Description: UserVo
 * @Version 1.0
 */
@Data
public class UserVo {
    private String id;

    private String username;

    private String faceImage;

    private String faceImageBig;

    private String nickname;

    private String qrcode;
}
