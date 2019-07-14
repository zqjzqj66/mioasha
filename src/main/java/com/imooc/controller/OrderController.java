package com.imooc.controller;

import com.imooc.exception.ErrListEnum;
import com.imooc.exception.MyException;
import com.imooc.response.CommonReturnType;
import com.imooc.service.OrderService;
import com.imooc.service.model.OrderModel;
import com.imooc.service.model.UserModel;
import com.imooc.util.threadlocal.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: OrderController
 * @date 2019/6/9 19:16
 */
@RestController
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/createOrder")
    public CommonReturnType createOrder(@RequestParam("itemId")Integer itemId,
                                        @RequestParam("amount")Integer amount,
                                        @RequestParam(value = "promoId",required = false)Integer promoId){
        //从threadlocal里面获取userid
        String userId = UserThreadLocal.get();
        OrderModel order = orderService.createOrder(Integer.valueOf(userId), itemId,promoId, amount);
        return CommonReturnType.success(order);
    }

}
