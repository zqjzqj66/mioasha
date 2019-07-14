package com.imooc.service;

import com.imooc.service.model.PromoModel;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: PromoService
 * @date 2019/6/9 22:04
 */
public interface PromoService {

    PromoModel getPromoByItemId(Integer itemId);
}
