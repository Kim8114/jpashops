package jpabook.jpashops.repository;

import jpabook.jpashops.domain.Order;
import jpabook.jpashops.repository.order.simplequery.SimpleOrderQueryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch){
        String sql = "select o from Order o join o.member m where o.status=:status and m.name like :name";


        return em.createQuery(sql, Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000)
                .getResultList();

    }


    public List<Order> findAllWithMemberDelivery() {

        String sql1 ="select o from Order o join fetch o.member m join fetch o.delivery d";
        return em.createQuery(sql1, Order.class).getResultList();
    }

    public List<SimpleOrderQueryDTO> findOrderDTOs(){
        return em.createQuery("select new jpabook.jpashops.repository.SimpleOrderQueryDTO(o.id, m.name, " +
                "o.orderDate, o.status, d.address) from Order o join o.member m join o.delivery d", SimpleOrderQueryDTO.class)
        .getResultList();
    }

}
