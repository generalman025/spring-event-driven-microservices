package com.appsdeveloper.estore.ProductService.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.appsdeveloper.estore.ProductService.core.data.ProductEntity;
import com.appsdeveloper.estore.ProductService.core.data.ProductRepository;
import com.appsdeveloper.estore.ProductService.query.rest.ProductRestModel;

@Component
public class ProductsQueryHandler {
	
	private final ProductRepository productsRepository;
	
	public ProductsQueryHandler(ProductRepository productsRepository) {
		this.productsRepository = productsRepository;
	}
	
	@QueryHandler
	public List<ProductRestModel> findProducts(FindProductsQuery query){
		
		List<ProductRestModel> productRest = new ArrayList<>();
		
		List<ProductEntity> storedProducts = productsRepository.findAll();
		
		for(ProductEntity productEntity: storedProducts) {
			ProductRestModel productRestModel = new ProductRestModel();
			BeanUtils.copyProperties(productEntity, productRestModel);
			productRest.add(productRestModel);
		}
		
		return productRest;
		
	}

}
