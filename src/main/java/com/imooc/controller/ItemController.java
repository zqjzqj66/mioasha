package com.imooc.controller;

import com.imooc.constant.ItemPromoStatusEnum;
import com.imooc.constant.PromoStatusEnum;
import com.imooc.controller.viewobject.ItemVO;
import com.imooc.response.CommonReturnType;
import com.imooc.service.ItemService;
import com.imooc.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: ItemController
 * @date 2019/6/5 10:20
 */
@RestController
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @GetMapping("/list")
    public CommonReturnType listItem(){
        List<ItemModel> itemModels = itemService.listItem();

        List<ItemVO> collect = itemModels.stream()
                .map(t -> convertFormItemModel(t)).collect(Collectors.toList());
        return CommonReturnType.success(collect);
    }

    @PostMapping("/create")
    public CommonReturnType createItem(@RequestParam("title")String title,
                                       @RequestParam("description")String description,
                                       @RequestParam("price")BigDecimal price,
                                       @RequestParam("imgUrl")String imgUrl,
                                       @RequestParam("stock")Integer stock){
        ItemModel itemModel = new ItemModel();
        itemModel.setStock(stock);
        itemModel.setDescription(description);
        itemModel.setImgUrl(imgUrl);
        itemModel.setPrice(price);
        itemModel.setTitle(title);

        ItemModel item = itemService.createItem(itemModel);
        return CommonReturnType.success(item);
    }

    @GetMapping("/getItemById")
    public CommonReturnType getItemById(@RequestParam("id")Integer id){
        ItemModel itemById = itemService.getItemById(id);
        ItemVO itemVO = convertFormItemModel(itemById);
        return CommonReturnType.success(itemVO);
    }

    private ItemVO convertFormItemModel(ItemModel itemModel) {
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO );

        if(itemModel.getPromoModel()==null)
            itemVO.setPromoStatus(ItemPromoStatusEnum.NO.getStatus());
        else{
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus().getStatus());
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
            itemVO.setPromoId(itemModel.getPromoModel().getPromoId());
            itemVO.setStartTime(itemModel.getPromoModel().getStartTime());
        }
        return itemVO;
    }


}
