package com.njeport.track.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.njeport.track.common.utils.PagingUtils.Paging;

import java.util.List;

/**
 * @author kongming
 * @date 2019/12/29
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonPagingVO<T> {
    private List<T> records;
    private int size; //每页记录数
    private int pages; //总页数

    private int total; //当前返回记录数
    private int current; //当前页号

    public static <T> CommonPagingVO<T> getCommonPagingVO(Paging paging, List<T> records) {
        return new CommonPagingVO<>(records, paging.getPageSize(), paging.getTotalPage(), paging.getTotalCount(), paging.getPageNo());
    }
}
