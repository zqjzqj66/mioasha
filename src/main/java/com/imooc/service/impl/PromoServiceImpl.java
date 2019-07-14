package com.imooc.service.impl;

import com.imooc.constant.PromoStatusEnum;
import com.imooc.dao.PromoDOMapper;
import com.imooc.dataobject.PromoDO;
import com.imooc.exception.ErrListEnum;
import com.imooc.exception.MyException;
import com.imooc.service.PromoService;
import com.imooc.service.model.PromoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: PromoServiceImpl
 * @date 2019/6/9 22:05
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);
        PromoModel promoModel = convertFromPromoDO(promoDO);

        if(promoModel==null)
            return null;

        if(promoModel.getStartTime().isAfter(LocalDateTime.now()))
            promoModel.setStatus(PromoStatusEnum.START);
        else if(promoModel.getEndTime().isBefore(LocalDateTime.now()))
            promoModel.setStatus(PromoStatusEnum.END);
        else
            promoModel.setStatus(PromoStatusEnum.ONGOING0);

        return promoModel;
    }

    private PromoModel convertFromPromoDO(PromoDO promoDO) {
        if(promoDO==null){
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO, promoModel);
        return promoModel;
    }
}
