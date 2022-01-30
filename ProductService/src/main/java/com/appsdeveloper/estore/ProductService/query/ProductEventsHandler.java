package com.appsdeveloper.estore.ProductService.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.appsdeveloper.estore.ProductService.core.data.ProductEntity;
import com.appsdeveloper.estore.ProductService.core.data.ProductRepository;
import com.appsdeveloper.estore.ProductService.core.event.ProductCreateEvent;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {
	
	private final ProductRepository productRepository;
	
	public ProductEventsHandler(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@ExceptionHandler(resultType = Exception.class)
	public void handle(Exception exception) throws Exception {
		throw exception;
	}
	
	@ExceptionHandler(resultType = IllegalArgumentException.class)
	public void handle(IllegalArgumentException exception) {
		// Log error message
	}

	@EventHandler
	public void on(ProductCreateEvent event) throws Exception {
		ProductEntity productEntity = new ProductEntity();
		BeanUtils.copyProperties(event, productEntity);
		
		try {
			productRepository.save(productEntity);
		}catch(IllegalArgumentException ex) {
			ex.printStackTrace();
		}
	}
}

