package com.attiWell.admin.goods.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.attiWell.goods.vo.GoodsVO;
import com.attiWell.goods.vo.ImageFileVO;
import com.attiWell.order.vo.OrderVO;

@Repository("adminGoodsDAO")
public class AdminGoodsDAOImpl  implements AdminGoodsDAO{
	@Autowired
	private SqlSession sqlSession;
	
	// newGoodsMap은 제너럴 타입 정해져 있지 않음 -> object반환 하지만 object->int로 변환이 불가능하므로 String-> int형으로 반환
	// 시퀀스로 자동으로 goods_id생성해서 반환 
	@Override
	public int insertNewGoods(Map newGoodsMap) throws DataAccessException {
		sqlSession.insert("mapper.admin.goods.insertNewGoods",newGoodsMap);
		return Integer.parseInt((String)newGoodsMap.get("goods_id"));
	}
	
	//파일리스트안에 있는 ImageFileVO객체들 반복문 돌려서 DB에 삽입
	@Override
	public void insertGoodsImageFile(List fileList)  throws DataAccessException {
		for(int i=0; i<fileList.size();i++){
			ImageFileVO imageFileVO=(ImageFileVO)fileList.get(i);
			sqlSession.insert("mapper.admin.goods.insertGoodsImageFile",imageFileVO);
		}
	}
		
	@Override
	public List<GoodsVO>selectNewGoodsList(Map condMap) throws DataAccessException {
		ArrayList<GoodsVO>  goodsList=(ArrayList)sqlSession.selectList("mapper.admin.goods.selectNewGoodsList",condMap);
		return goodsList;
	}
	
	@Override
	public GoodsVO selectGoodsDetail(int goods_id) throws DataAccessException{
		GoodsVO goodsBean = new GoodsVO();
		goodsBean=(GoodsVO)sqlSession.selectOne("mapper.admin.goods.selectGoodsDetail",goods_id);
		return goodsBean;
	}
	
	@Override
	public List selectGoodsImageFileList(int goods_id) throws DataAccessException {
		List imageList=new ArrayList();
		imageList=(List)sqlSession.selectList("mapper.admin.goods.selectGoodsImageFileList",goods_id);
		return imageList;
	}
	
	@Override
	public void updateGoodsInfo(Map goodsMap) throws DataAccessException{
		sqlSession.update("mapper.admin.goods.updateGoodsInfo",goodsMap);
	}
	
	@Override
	public void deleteGoodsImage(int image_id) throws DataAccessException{
		sqlSession.delete("mapper.admin.goods.deleteGoodsImage",image_id);
	}
	
//	@Override
//	public void deleteGoodsImage(List fileList) throws DataAccessException{
//		int image_id;
//		for(int i=0; i<fileList.size();i++){
//			ImageFileVO bean=(ImageFileVO) fileList.get(i);
//			image_id=bean.getImage_id();
//			sqlSession.delete("mapper.admin.goods.deleteGoodsImage",image_id);	
//		}
//	}

	@Override
	public List<OrderVO> selectOrderGoodsList(Map condMap) throws DataAccessException{
		List<OrderVO>  orderGoodsList=(ArrayList)sqlSession.selectList("mapper.admin.selectOrderGoodsList",condMap);
		return orderGoodsList;
	}	
	
	@Override
	public void updateOrderGoods(Map orderMap) throws DataAccessException{
		sqlSession.update("mapper.admin.goods.updateOrderGoods",orderMap);
		
	}

	@Override
	public void updateGoodsImage(List<ImageFileVO> imageFileList) throws DataAccessException {
		
		for(int i=0; i<imageFileList.size();i++){
			ImageFileVO imageFileVO = imageFileList.get(i);
			sqlSession.update("mapper.admin.goods.updateGoodsImage",imageFileVO);	
		}
		
	}



	@Override
	public void deleteGoods(int goods_id) throws DataAccessException{
		sqlSession.delete("mapper.admin.goods.deleteGoods",goods_id);
		
	}

	

}
