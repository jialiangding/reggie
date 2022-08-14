package jialiang_ding.reggie.common;

import lombok.Data;

import java.util.List;

@Data
public class PageBase<T> {
    private  Integer counts;
    private  Integer page;
    private  Integer pageSize;
    private List<T> records;
}
