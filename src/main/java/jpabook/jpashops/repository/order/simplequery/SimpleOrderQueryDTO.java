package jpabook.jpashops.repository.order.simplequery;

import jpabook.jpashops.domain.Address;
import jpabook.jpashops.domain.OrderStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SimpleOrderQueryDTO {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate; //주문시간
    private OrderStatus orderStatus;
    private Address address;


    public SimpleOrderQueryDTO(Long orderId, String name, LocalDateTime orderDate,
                               OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
