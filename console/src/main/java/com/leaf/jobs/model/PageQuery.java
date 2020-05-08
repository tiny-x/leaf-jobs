/**
 *
 */
package com.leaf.jobs.model;

/**
 *
 * @author yefei
 */
public class PageQuery {

    private int page = 1;

    private int limit = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 不需要分页
     */
    public void noPage() {
        this.page = -1;
    }
}
