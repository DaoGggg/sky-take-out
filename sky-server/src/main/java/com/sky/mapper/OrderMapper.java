package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {
    /**
     * 插入一条数据
     * @param orders
     */
    void insert(Orders orders);

    /**
     * 根据订单号查询
     * @param outTradeNo
     * @return
     */

    @Select("select * from orders where number = #{outTradeNo}")
    Orders getByNumber(String outTradeNo);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    /**
     * 条件分页查询
     * @param ordersPageQueryDTO
     * @return
     */

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 根据id删除订单
     * @param id
     */
    @Delete("delete from orders where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据状态统计订单数量
     * @param status
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer status);

    /**
     * 根据订单id接单
     * @param ordersConfirmDTO
     */
    @Update("update orders set status = #{status} where id = #{id}")
    void confirmById(OrdersConfirmDTO ordersConfirmDTO);
}
