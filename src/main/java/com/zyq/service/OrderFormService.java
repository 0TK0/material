package com.zyq.service;

import com.zyq.dao.ComponentsDao;
import com.zyq.dao.OrderDao;
import com.zyq.dao.OrderFormDao;
import com.zyq.dao.UserDao;
import com.zyq.enums.ApplyDealEnum;
import com.zyq.model.*;
import com.zyq.util.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TK on 2016/12/9.
 */
@Service
public class OrderFormService {

    @Autowired
    OrderFormDao orderFormDao;
    @Autowired
    UserDao userDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    ComponentsDao componentsDao;

    private static final Logger logger = LoggerFactory.getLogger(OrderFormService.class);
    public List<OrderFormModel> getAll(){
        List<OrderForm> list = null;
        try {
            list = orderFormDao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        List<OrderFormModel> orderFormModels = toModel(list);
        return orderFormModels;
    }

    public List<OrderFormModel> getAllFlag(int flag){
        List<OrderForm> list = new ArrayList<>();
        if (flag == ApplyDealEnum.DEALED.getCode()){
            try {
                list = orderFormDao.selectALLDealed();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }else{
            try {
                list = orderFormDao.selectALLNotDealed();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
        List<OrderFormModel> orderFormModelList = toModel(list);
        return orderFormModelList;
    }

    public List<OrderFormModel> getOrderFormByName(String name){
        List<OrderForm> list = null;
        try {
            list = orderFormDao.selectOrderFormByName("%"+name+"%");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        List<OrderFormModel> orderFormModelList = toModel(list);
        return orderFormModelList;
    }

    public int getLastOrderFormId(){
        OrderForm orderForm = null;
        try {
            orderForm = orderFormDao.selectLastOrderFormId();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        if (orderForm == null){
            return 1;
        }else{
            return orderForm.getOrderFormId()+1;
        }
    }

    public HashMap<String,Object> addOrderForm(int userIdAppy,String name){
        HashMap<String,Object> map = new HashMap<>();
        if(StringUtils.isBlank(name)){
            map.put("msgName","订单名不能为空");
            return map;
        }
        OrderForm orderForm = new OrderForm();
        orderForm.setName(name);
        orderForm.setUserIdApply(userIdAppy);
        orderForm.setStatus(0);
        orderForm.setIsDelete(0);
        orderForm.setCreateTime(new Date());
        orderForm.setModifiedTime(new Date());
        try {
            orderFormDao.inserOrderForm(orderForm);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return map;
    }

    public OrderFormModel getOrderFormById(int orderFormId){
        OrderForm orderForm = null;
        try {
            orderForm = orderFormDao.selectOrderFormById(orderFormId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return toModel(orderForm);
    }

    public HashMap<String,Object> modify(int orderFormId,String name,String applyName,String dealName){
        HashMap<String,Object> map = new HashMap<>();
        if (StringUtils.isBlank(name)){
            map.put("msgName","订单名不能为空");
            return map;
        }
        if(StringUtils.isBlank(applyName)){
            map.put("msgApplyName","申请人不能为空");
            return map;
        }
        OrderForm orderForm = orderFormDao.selectOrderFormById(orderFormId);
        orderForm.setName(name);
        User userApply = null;
        try {
            userApply = userDao.selectByNameOne(applyName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        orderForm.setUserIdApply(userApply.getUserId());
        orderForm.setModifiedTime(new Date());
        orderForm.setStatus(0);
        if (!StringUtils.isBlank(dealName)){
            User userDeal = null;
            try {
                userDeal = userDao.selectByNameOne(dealName);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
            orderForm.setUserIdDeal(userDeal.getUserId());
        }
        try {
            orderFormDao.update(orderForm);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return map;
    }

    public HSSFWorkbook getComponentEXCEL(int orderFormId,String dealName){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("元器件订单");
        for(int i = 1;i <= 5;i++){
            sheet.setColumnWidth(i,5000);
        }

        //生成内容字体对象
        HSSFFont fontContent = workbook.createFont();
        fontContent.setFontHeightInPoints((short) 10);
        fontContent.setFontName("微软雅黑");
        //font.setColor(HSSFColor.DAR);
        fontContent.setBoldweight((short) 0.8);
        //生成内容样式对象
        HSSFCellStyle styleContent = workbook.createCellStyle();
        styleContent.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleContent.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleContent.setFont(fontContent); //调用字体样式对象
        styleContent.setWrapText(true);
        //边框设置
        styleContent.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        styleContent.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        styleContent.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        styleContent.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        //生成主标题字体对象
        HSSFFont fontHead1 = workbook.createFont();
        fontHead1.setFontHeightInPoints((short) 15);
        fontHead1.setFontName("微软雅黑");
        //font.setColor(HSSFColor.DAR);
        fontHead1.setBoldweight((short) 1);
        //生成主标题样式对象
        HSSFCellStyle styleHead1 = workbook.createCellStyle();
        styleHead1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHead1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleHead1.setFont(fontHead1); //调用字体样式对象
        styleHead1.setWrapText(true);
        //边框设置
        styleHead1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        styleHead1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        styleHead1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        styleHead1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        //生成备注字体对象
        HSSFFont fontRemark = workbook.createFont();
        fontRemark.setFontHeightInPoints((short) 10);
        fontRemark.setFontName("新宋体");
        //font.setColor(HSSFColor.DAR);
        fontRemark.setBoldweight((short) 0.8);
        //生成备注样式对象
        HSSFCellStyle styleRemark = workbook.createCellStyle();
        styleRemark.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleRemark.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleRemark.setFont(fontRemark); //调用字体样式对象
        styleRemark.setWrapText(true);


        //边框设置
        HSSFCellStyle styleBorder = workbook.createCellStyle();
        styleBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        styleBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        styleBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        styleBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        HSSFRow row;
        HSSFCell cell;
        row = sheet.createRow(1);
        for (int i=1;i<=5;i++){
            cell = row.createCell(i);
            cell.setCellStyle(styleHead1);
            switch (i){
                case 1 : cell.setCellValue("Name");break;
                case 2 : cell.setCellValue("Footprint");break;
                case 3 : cell.setCellValue("Value");break;
                case 4 : cell.setCellValue("Quantity");break;
                default: cell.setCellValue("Remark");
            }
        }
        List<Integer> componentIdList = orderDao.selectNameByOrderFormIdDis(orderFormId);
        HashMap<String,List<Integer>> map = new HashMap<>();
        for (int componentId : componentIdList) {
            Components component = componentsDao.selectById(componentId);
            if (component != null){
                String name = component.getName();
                if (map.containsKey(name)){
                    List<Integer> list = map.get(name);
                    list.add(componentId);
                    map.put(name,list);
                }else{
                    List<Integer> list = new ArrayList<>();
                    list.add(componentId);
                    map.put(name,list);
                }
            }
        }

        int curRowNum = 2;//所在编辑行
        for (HashMap.Entry<String,List<Integer>> entry : map.entrySet()){
            int mergeRowCount = entry.getValue().size();
            int colNum=1;
            if(mergeRowCount > 1){
                sheet.addMergedRegion(new CellRangeAddress(curRowNum,curRowNum+mergeRowCount-1,colNum,colNum));
            }
            row = sheet.createRow(curRowNum);
            cell = row.createCell(colNum);
            cell.setCellStyle(styleContent);
            cell.setCellValue(entry.getKey());
            colNum = 2;
            int rowNum = curRowNum;
            int flag = 1;
            for(int i : entry.getValue()){
                Components component = componentsDao.selectById(i);
                if (flag != 1){
                    row = sheet.createRow(++rowNum);
                }
                flag = 2;
                //封装
                cell = row.createCell(colNum++);
                cell.setCellStyle(styleContent);
                cell.setCellValue(component.getFootprint());
                //值
                cell = row.createCell(colNum++);
                cell.setCellStyle(styleContent);
                cell.setCellValue(component.getValue());
                //数量
                cell = row.createCell(colNum++);
                cell.setCellStyle(styleContent);
                Order order = new Order();
                order.setOrderFormId(orderFormId);
                order.setComponentId(i);
                int quantity = orderDao.selectOrderByComponentIdAndOrderFormId(order);
                cell.setCellValue(quantity);
                //备注
                cell = row.createCell(colNum++);
                cell.setCellStyle(styleContent);
                cell.setCellValue(component.getRemark());
                colNum = 2;
            }
            curRowNum = curRowNum + mergeRowCount;
        }

        //申请人 申请日期
        OrderForm orderForm = orderFormDao.selectOrderFormById(orderFormId);
        if (orderForm != null){
            User user = userDao.selectById(orderForm.getUserIdApply());
            row = sheet.createRow(curRowNum+1);
            cell = row.createCell(4);
            cell.setCellStyle(styleRemark);
            cell.setCellValue("申请人");

            cell = row.createCell(5);
            cell.setCellStyle(styleRemark);
            cell.setCellValue(user.getName());

            row = sheet.createRow(curRowNum+2);
            cell = row.createCell(4);
            cell.setCellStyle(styleRemark);
            cell.setCellValue("申请日期");

            cell = row.createCell(5);
            cell.setCellStyle(styleRemark);
            cell.setCellValue(TimeUtil.formatNYR(orderForm.getModifiedTime()));
        }
        User user = userDao.selectByNameOne(dealName);
        orderForm.setUserIdDeal(user.getUserId());
        orderForm.setStatus(1);
        orderFormDao.update(orderForm);
        return workbook;
    }

    public List<OrderFormModel> getOrderFormByUserName(String userName){
        User user = null;
        try {
            user = userDao.selectByNameOne(userName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        List<OrderFormModel> list = new ArrayList<>();
        if (user != null){
            List<OrderForm> orderFormList = null;
            try {
                orderFormList = orderFormDao.selectOrderFormByUserIdApply(user.getUserId());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
            list = toModel(orderFormList);
        }
        return list;
    }

    public void removeOrderFormById(int orderFormId){
        try {
            orderFormDao.removeOrderFormById(orderFormId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    private OrderFormModel toModel(OrderForm orderForm){
        OrderFormModel orderFormModel = new OrderFormModel();
        assembly(orderForm,orderFormModel);
        return orderFormModel;
    }

    private List<OrderFormModel> toModel(List<OrderForm> list){
        List<OrderFormModel> orderFormModelList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            for (OrderForm orderForm : list){
                OrderFormModel orderFormModel = new OrderFormModel();
                assembly(orderForm,orderFormModel);
                orderFormModelList.add(orderFormModel);
            }
        }
        return orderFormModelList;
    }
    private void assembly(OrderForm orderForm,OrderFormModel orderFormModel){
        if (orderForm != null){
            orderFormModel.setOrderFormId(orderForm.getOrderFormId());
            orderFormModel.setName(orderForm.getName());
            orderFormModel.setOrderId(orderForm.getOrderFormId());
            User applyUser = null;
            User dealUser = null;
            try {
                applyUser = userDao.selectById(orderForm.getUserIdApply());
                dealUser = userDao.selectById(orderForm.getUserIdDeal());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
            orderFormModel.setApplyUser(applyUser);
            orderFormModel.setDealUser(dealUser);
            orderFormModel.setStatus(orderForm.getStatus());
            orderFormModel.setIsDelete(orderForm.getIsDelete());
            orderFormModel.setCreateTime(TimeUtil.formatNYR(orderForm.getCreateTime()));
            orderFormModel.setModifiedTime(TimeUtil.formatNYR(orderForm.getModifiedTime()));
        }
    }
}
