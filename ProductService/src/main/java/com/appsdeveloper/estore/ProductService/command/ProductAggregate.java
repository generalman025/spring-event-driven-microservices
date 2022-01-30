package com.appsdeveloper.estore.ProductService.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.appsdeveloper.estore.ProductService.core.event.ProductCreateEvent;

@Aggregate
public class ProductAggregate {
	
		@AggregateIdentifier
		private String productId;
		private String title;
		private BigDecimal price;
		private Integer quantity;

		public ProductAggregate() {
			
		}
		
		@CommandHandler
		public ProductAggregate(CreateProductCommand createProductCommand) {
			// Validate Create Product Command
			
			if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("Price cannot be less or equal than zero");
			}
			
			if(createProductCommand.getTitle() == null || createProductCommand.getTitle().isBlank()) {
				throw new IllegalArgumentException("Title cannot be empty");
			}
			
			ProductCreateEvent productCreatedEvent = new ProductCreateEvent();
			
			BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
			
			AggregateLifecycle.apply(productCreatedEvent);
		}
		
		@EventSourcingHandler
		public void on(ProductCreateEvent productCreatedEvent) {
			this.productId = productCreatedEvent.getProductId();
			this.price = productCreatedEvent.getPrice();
			this.title = productCreatedEvent.getTitle();
			this.quantity = productCreatedEvent.getQuantity();
		}
}
