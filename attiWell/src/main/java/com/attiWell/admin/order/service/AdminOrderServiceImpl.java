package com.attiWell.admin.order.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.attiWell.admin.goods.dao.AdminGoodsDAO;
import com.attiWell.admin.order.dao.AdminOrderDAO;
import com.attiWell.goods.vo.GoodsVO;
import com.attiWell.goods.vo.ImageFileVO;
import com.attiWell.member.vo.MemberVO;
import com.attiWell.order.vo.OrderVO;


@Service("adminOrderService")
@Transactional(propagation=Propagation.REQUIRED)
public class AdminOrderServiceImpl implements AdminOrderService {
	@Autowired
	private AdminOrderDAO adminOrderDAO;
	
	public List<OrderVO>listNewOrder(Map condMap) throws Exception{
		return adminOrderDAO.selectNewOrderList(condMap);
	}
	@Override
	public void  modifyDeliveryState(Map deliveryMap) throws Exception{
		adminOrderDAO.updateDeliveryState(deliveryMap);
	}
	@Override
	public Map orderDetail(int order_id) throws Exception{
		Map orderMap=new HashMap();
		ArrayList<OrderVO> orderList =adminOrderDAO.selectOrderDetail(order_id);
		// 현재 List에는 order_id에 맞는 하나의 ordervo밖에 없음 이걸 그냥 deliveryInfo에 넣어준거임
		OrderVO deliveryInfo=(OrderVO)orderList.get(0);
		// deliveryInfo의 필드에 member_id필드 있음 이걸 꺼내줌
		String member_id=(String)deliveryInfo.getMember_id();
		// member_id를 가지고 member_id조건의 memberVO반환
		MemberVO orderer=adminOrderDAO.selectOrderer(member_id);
		orderMap.put("orderList",orderList);
		orderMap.put("deliveryInfo",deliveryInfo);
		orderMap.put("orderer", orderer);
		return orderMap;
	}

	
	

}
