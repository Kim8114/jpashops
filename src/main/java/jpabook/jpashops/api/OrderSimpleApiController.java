package jpabook.jpashops.api;


import jpabook.jpashops.domain.Address;
import jpabook.jpashops.domain.Order;
import jpabook.jpashops.domain.OrderStatus;
import jpabook.jpashops.repository.OrderRepository;
import jpabook.jpashops.repository.OrderSearch;
import jpabook.jpashops.repository.order.simplequery.SimpleOrderQueryDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;


@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    /**
     * V1. 엔티티 직접 노출
     * - Hibernate5Module 모듈 등록, LAZY=null 처리
     * - 양방향 관계 문제 발생 -> @JsonIgnore
     */

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기환
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderQueryDTO> ordersV4() {
        return orderRepository.findOrderDTOs();
    }

   @Data
    static class SimpleOrderDto{
       private Long orderId;
       private String name;
       private LocalDateTime orderDate; //주문시간
       private OrderStatus orderStatus;
       private Address address;

       public SimpleOrderDto(Order order) {
           orderId = order.getId();
           name = order.getMember().getName();
           orderDate = order.getOrderDate();
           orderStatus = order.getStatus();
           address = order.getDelivery().getAddress();
       }
    }
}
