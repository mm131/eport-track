package com.njeport.track.common.utils;

import lombok.Getter;

/**
 * @author kongming
 * @date 2019/12/27
 */
public class PagingUtils {

    private static final int MAX_PAGE_SIZE = 1000;

    public static Paging getPaging(int total, int pageNum, int pageSize) {
        Paging ret = new Paging();
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        int totalPage = total / pageSize;
        int remainder = total % pageSize;
        if (remainder > 0) {
            totalPage += 1;
        }
        if (totalPage <= 0) {
            totalPage = 1;
        }
        if (pageNum > totalPage) {
            pageNum = totalPage;
        }
        ret.pageNo = pageNum;
        ret.pageSize = pageSize;
        ret.totalCount = total;
        ret.totalPage = totalPage;
        return ret;
    }

    @Getter
    public static class Paging {
        @Override
        public String toString() {
            return "PagingData [totalCount=" + totalCount + ", totalPage="
                    + totalPage + ", pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
        }
        private int totalCount;
        private int totalPage;
        private int pageNo;
        private int pageSize;
    }
}
