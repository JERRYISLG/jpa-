package cn.itcast;

import cn.itcast.domain.Customer;
import cn.itcast.utils.JPAUtil;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

public class JpaTest {

    @Test
    public void test() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //获取事务对象
        EntityTransaction transaction = entityManager.getTransaction();
        //开启事务
        transaction.begin();
        //保存操作
        Customer customer = new Customer();
        customer.setCustName("传智播客");
        entityManager.persist(customer);
        //提交事务(回滚事务)
        transaction.commit();
        //释放资源
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testAdd() {
        Customer c = new Customer();
        c.setCustName("传智播客");
        c.setCustAddress("北京市昌平区");
        c.setCustIndustry("IT教育");
        c.setCustLevel("VIP客户");
        c.setCustPhone("010-84389340");
        c.setCustSource("网络");
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            //获取实体管理对象
            entityManager = JPAUtil.getEntityManager();
            //获取事务对象
            tx = entityManager.getTransaction();
            //开启事务
            tx.begin();
            //保存

            entityManager.persist(c);
            //提交事务
            tx.commit();

        } catch (Exception e) {
            //事务回滚
            tx.rollback();
            e.printStackTrace();
        } finally {
            //释放资源
            entityManager.close();
        }
    }

    //修改
    @Test
    public void testMerge() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = JPAUtil.getEntityManager();
            tx = entityManager.getTransaction();

            tx.begin();
            //执行操作
            Customer customer = entityManager.find(Customer.class, 1L);
            customer.setCustName("江苏传智学院");
            entityManager.clear();
            entityManager.merge(customer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

    }

    //删除
    @Test
    public void testRemove() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = JPAUtil.getEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Customer customer = entityManager.find(Customer.class, 1L);
            entityManager.remove(customer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    //延迟加载
    @Test
    public void loadOne(){
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = JPAUtil.getEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Customer customer = entityManager.getReference(Customer.class, 2L);
            tx.commit();
            System.out.println(customer);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    //查询全部
    @Test
    public void findAll(){
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = JPAUtil.getEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            //创建query对象
            String jpql="from Customer";
            Query query = entityManager.createQuery(jpql);
            //查询并得到结果集
            List list = query.getResultList();
            for (Object o : list) {
                System.out.println(list);
            }
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    //分页查询
    @Test
    public void findPage(){
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = JPAUtil.getEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            //创建query对象
            String jpql="from Customer";
            Query query = entityManager.createQuery(jpql);
            //起始索引
            query.setFirstResult(0);
            //每页显示条数
            query.setMaxResults(2);
            //查询结果并返回
            List resultList = query.getResultList();
            for (Object o : resultList) {
                System.out.println(o);
            }
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    //条件查询
    @Test
    public void findCondition(){
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = JPAUtil.getEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            //创建query对象
            String jpql="from Customer where custName like ?";
            Query query = entityManager.createQuery(jpql);
            query.setParameter(1,"传智播客%");

            //查询结果并返回
            List resultList = query.getResultList();
            for (Object o : resultList) {
                System.out.println(o);
            }
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    //排序查询
    @Test
    public void testOrder(){
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = JPAUtil.getEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            //创建query对象
            String jpql="from Customer order by custId desc ";
            Query query = entityManager.createQuery(jpql);


            //查询结果并返回
            List resultList = query.getResultList();
            for (Object o : resultList) {
                System.out.println(o);
            }
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    //统计查询
    @Test
    public void findCount(){
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = JPAUtil.getEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            //创建query对象
            String jpql="select count(custId) from Customer ";
            Query query = entityManager.createQuery(jpql);
            Object result = query.getSingleResult();

            //查询结果并返回
            System.out.println(result);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}
