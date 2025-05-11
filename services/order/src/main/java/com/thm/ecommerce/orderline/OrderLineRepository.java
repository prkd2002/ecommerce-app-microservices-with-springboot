package com.thm.ecommerce.orderline;

import com.thm.ecommerce.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
    List<OrderLine> order(Order order);

    List<OrderLine> findAllByOrderId(Integer orderId);
}
