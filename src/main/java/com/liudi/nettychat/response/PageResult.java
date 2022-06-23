package com.liudi.nettychat.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体
 *
 * @Description:
 * @Author: ckk
 * @CreateDate: 2022/2/16 10:09
 */
@Data
@Builder
public class PageResult implements Serializable {

    private static final long serialVersionUID = 6787526842334119390L;

    private long totalCount;
    private List<?> content;
    private long totalPage;


    public static PageResult setPage(IPage<?> page) {
        return PageResult.builder()
                .totalCount(page.getTotal())
                .totalPage(page.getPages())
                .content(page.getRecords())
                .build();
    }

    public static PageResult setPage(IPage<?> page, List<?> content) {
        return PageResult.builder()
                .totalCount(page.getTotal())
                .totalPage(page.getPages())
                .content(content)
                .build();
    }
}
