package com.gyh.fleacampus.model;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created by gyh on 2018/10/19.
 * @apiDefine PageView
 * @apiSuccess (返回) {Long} pageNum 当前页号
 * @apiSuccess (返回) {Long} pageSize 每页条数
 * @apiSuccess (返回) {Long} pages 总页数
 * @apiSuccess (返回) {Long} total 总条数
 */
public class PageView<T> {

    private int pageNum;

    private int pageSize;

    private long total;

    private List<T> list;

    public <K, P extends List<K>> PageView(P pojo) {
        if (pojo instanceof Page) {
            BeanUtils.copyProperties(((Page<?>) pojo).toPageInfo(), this);
        } else {
            BeanUtils.copyProperties(new PageInfo<>(pojo), this);
        }
    }

    public static <K, P extends List<K>> PageView<K> build(P pojo) {
        return new PageView<>(pojo);
    }

    public static int getPages(long total, int pageSize) {
        if (total == 0 || pageSize == 0) {
            return 0;
        }
        return (int) (total % pageSize == 0 ? (total / pageSize) : (total / pageSize + 1));
    }

    public int getPages(){
        return getPages(this.total, this.pageSize);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
