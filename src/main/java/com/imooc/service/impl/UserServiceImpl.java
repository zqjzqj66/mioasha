package com.imooc.service.impl;

import com.imooc.dao.UserDoMapper;
import com.imooc.dao.UserPasswordDoMapper;
import com.imooc.dataobject.UserDo;
import com.imooc.dataobject.UserPasswordDo;
import com.imooc.exception.ErrListEnum;
import com.imooc.exception.MyException;
import com.imooc.service.UserService;
import com.imooc.service.model.UserModel;
import com.imooc.validator.ValidationResult;
import com.imooc.validator.ValidatorImpl;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: UserServiceImpl
 * @date 2019/6/2 15:04
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDoMapper userDoMapper;

    @Autowired
    private UserPasswordDoMapper userPasswordDoMapper;



    @Override
    public UserModel getUserById(Integer id) {
        UserDo userDo = userDoMapper.selectByPrimaryKey(id);

        if (Objects.isNull(userDo))
            return null;

        UserPasswordDo userPasswordDo = userPasswordDoMapper.selectByUserId(id);

        UserModel userModel = convertFromDataObject(userDo, userPasswordDo);
        return userModel;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) {
        if(userModel==null)
            throw new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR);


        //使用java。validation来进行对象属性的判断，并抛出对应的值
        /*ValidationResult validate = ValidatorImpl.validate(userModel);
        if (validate.isHasErrors())
            throw new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR, validate.getErrorMsg());
*/
        UserDo userDo=convertFromModel(userModel);

        try{
            userDoMapper.insertSelective(userDo);
        }catch (DuplicateKeyException e){
            throw new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR,"手机号已经被注册");
        }

        userModel.setId(userDo.getId());

        UserPasswordDo userPasswordDo=convertPasswordFromModel(userModel);
        userPasswordDoMapper.insert(userPasswordDo);
    }


    //检验用户登录
    @Override
    public UserModel validateLogin(String telphone, String password) {

        //根据telphone获取 密码
        UserDo userDo = userDoMapper.selectByTelphone(telphone);

        if(userDo==null)
            throw new MyException(ErrListEnum.USER_LOGIN_FAIL);

        UserPasswordDo userPasswordDo = userPasswordDoMapper.selectByUserId(userDo.getId());

        //验证密码是否正确
        if (!StringUtils.equals(userPasswordDo.getEncrptPassword(),password ))
            throw new MyException(ErrListEnum.USER_LOGIN_FAIL);

        UserModel userModel = convertFromDataObject(userDo, userPasswordDo);
        return userModel;
    }

    private UserPasswordDo convertPasswordFromModel(UserModel userModel){
        if(userModel==null)
            throw new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR);

        UserPasswordDo userPasswordDo=new UserPasswordDo();
        userPasswordDo.setEncrptPassword(userModel.getPassword());
        userPasswordDo.setUserId(userModel.getId());
        return userPasswordDo;
    }

    private UserDo convertFromModel(UserModel userModel){
        if(userModel==null)
            throw new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR);

        UserDo userDo = new UserDo();
        BeanUtils.copyProperties(userModel, userDo );
        return userDo;
    }


    //从dataObject里面获取生成model
    private UserModel convertFromDataObject(UserDo userDo,UserPasswordDo userPasswordDo){
        if (Objects.isNull(userDo))
            return null;

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDo, userModel);

        if (!Objects.isNull(userPasswordDo))
            userModel.setPassword(userPasswordDo.getEncrptPassword());
        return userModel;
    }
}
