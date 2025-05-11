package com.thm.ecommerce.order;

import com.thm.ecommerce.customer.CustomerCLient;
import com.thm.ecommerce.exception.BusinessException;
import com.thm.ecommerce.kafka.OrderConfirmation;
import com.thm.ecommerce.kafka.OrderProducer;
import com.thm.ecommerce.orderline.OrderLineRequest;
import com.thm.ecommerce.orderline.OrderLineService;
import com.thm.ecommerce.payment.PaymentClient;
import com.thm.ecommerce.payment.PaymentRequest;
import com.thm.ecommerce.product.ProductClient;
import com.thm.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerCLient customerCLient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private  final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createdOrder(@Valid OrderRequest orderRequest) {
        // Check the customer  --> OpenFeign
        var customer = this.customerCLient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the provides ID") );


        // Purchase the products --> product-ms (RestTemplate)
        var purchasedProducts = this.productClient.purchaseProducts(orderRequest.products());


        // persist order
        var order = this.orderRepository.saveAndFlush(orderMapper.toOrder(orderRequest));


        // persist order lines
        for(PurchaseRequest purchaseRequest : orderRequest.products()){
            OrderLineRequest orderLineRequest = new OrderLineRequest(
                    null, order.getId(), purchaseRequest.productId(), purchaseRequest.quantity()
            );
            orderLineService.saveOrderLine(
                 orderLineRequest
            );

        }


        // start payment process
        var paymentRequest = new PaymentRequest(

                orderRequest.amount(),
                orderRequest.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer

        );

        paymentClient.requestOrderPayment(paymentRequest);


        // send the order confirmation --> notification-ms (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts

                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find order with ID: " + orderId));
    }
}



