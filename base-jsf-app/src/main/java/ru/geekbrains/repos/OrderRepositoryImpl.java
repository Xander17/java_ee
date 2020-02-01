package ru.geekbrains.repos;

import ru.geekbrains.repos.entities.Order;
import ru.geekbrains.repos.entities.OrderItem;
import ru.geekbrains.services.entities.OrderLine;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @TransactionAttribute
    @Override
    public int insert(Order order) {
        em.persist(order);
        return em.createQuery("select max(o.id) from Order o", Integer.class).getResultList().get(0);
    }

    @TransactionAttribute
    @Override
    public void update(OrderItem orderItem) {
        em.merge(orderItem);
    }

    @TransactionAttribute
    @Override
    public void deleteProduct(OrderItem orderItem) {
        Order order = getOrder(orderItem.getOrder().getId());
        orderItem = getOrderItem(orderItem.getId());
        order.getList().remove(orderItem);
        em.remove(orderItem);
    }

    @TransactionAttribute
    @Override
    public void deleteOrder(Order order) {
        order = getOrder(order.getId());
        em.remove(order);
    }

    @Override
    public OrderItem getOrderItem(int id) {
        return em.find(OrderItem.class, id);
    }

    @Override
    public Order getOrder(int id) {
        return em.find(Order.class, id);
    }

    @Override
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