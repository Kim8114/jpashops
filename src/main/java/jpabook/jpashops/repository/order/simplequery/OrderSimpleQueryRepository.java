package jpabook.jpashops.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;


        public List<SimpleOrderQueryDTO> findOrderDtos() {
            return em.createQuery("select new jpabook.jpashops.repository.order.simplequery.SimpleOrderQueryDTO(o.id, " +
                    "m.name, o.orderDate, o.status, d.address)" +
                    " from Order o" +
                            " join o.member m" +
                            " join o.delivery d", SimpleOrderQueryDTO.class)
                    .getResultList();
        }

}
