package ru.geekbrains.db;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class OrderRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Transactional
    public int insert(Order order) {
        em.persist(order);
        return em.createQuery("select max(o.id) from Order o", Integer.class).getResultList().get(0);
    }

    @Transactional
    public void update(OrderItem orderItem) {
        em.merge(orderItem);
    }

    @Transactional
    public void deleteProduct(OrderItem orderItem) {
        Order order = getOrder(orderItem.getOrder().getId());
        orderItem = getOrderItem(orderItem.getId());
        order.getList().remove(orderItem);
        em.remove(orderItem);
    }

    @Transactional
    public void deleteOrder(Order order) {
        order = getOrder(order.getId());
        em.remove(order);
    }

    public OrderItem getOrderItem(int id) {
        return em.find(OrderItem.class, id);
    }

    public Order getOrder(int id) {
        return em.find(Order.class, id);
    }

    public List<OrderLine> getOrders() {
        List<Object[]> orders = em.createQuery(
                "select o.id,count(i.id),sum(i.price*i.quantity) from Order o left join OrderItem i on o.id=i.order.id group by o.id",
                Object[].class).getResultList();
        List<OrderLine> orderLines = new ArrayList<>();
        for (Object[] values : orders) {
            Integer id = (Integer) values[0];
            Integer count = ((Long) values[1]).intValue();
            Double sum = values[2] == null ? null : (Double) values[2];
            orderLines.add(new OrderLine(id, count, sum));
        }
        return orderLines;
    }
}