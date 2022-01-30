package com.appsdeveloper.estore.ProductService.command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.appsdeveloper.estore.ProductService.core.data.ProductLookupEntity;
import com.appsdeveloper.estore.ProductService.core.data.ProductLookupRepository;
import com.appsdeveloper.estore.ProductService.core.event.ProductCreateEvent;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {
	
	private final ProductLookupRepository productLookupRepository;
	
	public ProductLookupEventsHandler(ProductLookupRepository productLookupRepository) {
		this.productLookupRepository = productLookupRepository;
	}
	
	@EventHandler
	public void on(ProductCreateEvent event) {
		ProductLookupEntity productLookupEntity = new ProductLookupEntity(event.getProductId(), event.getTitle());
		
		productLookupRepository.save(productLookupEntity);
	}
}

