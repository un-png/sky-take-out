package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    /**
     * 动态条件查询
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);
    /**
     * 根据id更新商品数量
     * @param shoppingCart
     * */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateById(ShoppingCart shoppingCart);
    /**
     * 插入购物车数据
     * @param shoppingCart
     * */
    @Insert("insert into shopping_cart (name, image, dish_id, setmeal_id, dish_flavor, number, amount, create_time, user_id) " +
    "values(#{name}, #{image}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{createTime}, #{userId})")
    void insert(ShoppingCart shoppingCart);
    /**
     * 根据id删除购物车数据
     * @param id
     * */
    @Delete("delete from shopping_cart where id=#{id} ;")
    void deleteById(Long id);
    /**
     * 根据用户id清空购物车数据
     * @param userId
     * */
    @Delete("delete from shopping_cart where user_id= #{userId} ;")
    void deleteByUserId(Long userId);
}
