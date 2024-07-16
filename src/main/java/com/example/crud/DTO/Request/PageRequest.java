package com.example.crud.DTO.Request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequest implements Pageable {
    Integer limit;
    Integer offset;
    private final Sort sort;

    public PageRequest(Integer limit, Integer offset, Sort sort) {
        this.limit = limit;
        this.offset = offset;
        this.sort = sort; // truyền cách sắp xếp riêng
    }

    public PageRequest(Integer limit, Integer offset) {
        this(limit, offset, Sort.unsorted()); // không sắp xếp
    }

    @SuppressWarnings("null")
    @Override
    public Pageable first() {
        return new PageRequest(getPageSize(), 0);
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public int getPageNumber() {
        return offset / limit; // lấy trang hiện tại

    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @SuppressWarnings("null")
    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    @SuppressWarnings("null")
    @Override
    public Pageable next() {
        // trang tiếp theo
        return new PageRequest(getPageSize(), (int) (getOffset() + getPageSize()));
    }

    public Pageable previous() {
        return hasPrevious() ? new PageRequest(getPageSize(), (int) (getOffset() - getPageSize())) : this;
    }

    @SuppressWarnings("null")
    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();

    }

    @SuppressWarnings("null")
    @Override
    public Pageable withPage(int pageNumber) {
        return new PageRequest(getPageSize(), pageNumber * getPageSize());
    }

}
