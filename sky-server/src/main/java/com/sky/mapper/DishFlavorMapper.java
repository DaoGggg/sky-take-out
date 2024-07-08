package com.sky.mapper;


import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味数据
     * @param flavors
     */
    void inserBatch(List<DishFlavor> flavors);

    /**
     * 根据dishid批量删除口味
     * @param ids
     */
    void deleteByDishids(List<Long> ids);
}
