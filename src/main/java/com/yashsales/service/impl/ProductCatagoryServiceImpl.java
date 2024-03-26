package com.yashsales.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.entity.ProductCatagory;
import com.yashsales.repository.ProductCatagoryRepository;
import com.yashsales.service.ProductCatagoryService;

@Service
public class ProductCatagoryServiceImpl implements ProductCatagoryService {

	@Autowired
	private ProductCatagoryRepository prodCatagoryRepo;
	
	@Override
	public Map<String, Object> getProductCatagories() {
		List<ProductCatagory> prodCatagories = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("ProductCatagoryList", null);

		prodCatagories = prodCatagoryRepo.findAll();
		responseMap.put("ProductCatagoryList", prodCatagories);

		return responseMap;
	}

	@Override
	public Map<String, Object> getActiveProductCatagories() {
		List<ProductCatagory> prodCatagories = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("ProductCatagoryList", null);

		prodCatagories = prodCatagoryRepo.findByProductCatagoryStatus();
		responseMap.put("ProductCatagoryList", prodCatagories);

		return responseMap;
	}

}
