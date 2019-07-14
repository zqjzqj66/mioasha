package com.imooc.service;

import com.imooc.service.model.UserModel;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: UserService
 * @date 2019/6/2 15:02
 */
public interface UserService {

    UserModel getUserById(Integer id);

    //注册用户
    void register(UserModel userModel);

    UserModel validateLogin(String telphone, String password);
}
