package com.liudi.nettychat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liudi.nettychat.entity.ChatMsg;
import com.liudi.nettychat.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author liuD
 * @Date 2022/6/20 9:49 上午
 * @PackageName:com.liudi.nettychat.mapper
 * @ClassName: ChatMsgMapper
 * @Description: ChatMsgMapper
 * @Version 1.0
 */
public interface ChatMsgMapper extends BaseMapper<ChatMsg> {
    /**
     * 批量更新
     */
    void batchUpdateMsgSigned(@Param("list") List<String> list);


}
