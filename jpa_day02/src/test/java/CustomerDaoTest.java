import cn.itcast.dao.CustomerDao;
import cn.itcast.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;

    /**
     * 保存客户
     */
    @Test
    public void save(){
        Customer c=new Customer();
        c.setCustName("黑马程序员");
        customerDao.save(c);
    }

    /**
     * 根据id查询
     */
    @Test
    public void findById(){
        Customer one = customerDao.findOne(5l);
        System.out.println(one);
    }
    /**
     * 更新操作
     */
    @Test
    public void update(){
        Customer one = customerDao.findOne(6l);
        if (one==null){
            return;
        }
        one.setCustName("黑马程序员");
        customerDao.save(one);
    }
    /**
     * 删除操作
     */
    @Test
    public void delete(){
        customerDao.delete(5l);
    }
    /**
     * 查询所有
     */
    @Test
    public void findAll(){
        List<Customer> allCustomer = customerDao.findAllCustomer();
        for (Customer customer : allCustomer) {
            System.out.println(customer);
        }
    }
    /**
     * 条件查询
     */
    @Test
    public void findACustomer(){
        Customer customer = customerDao.findCustomer2("传智播客1",3l);
        System.out.println(customer);
    }
    /**
     * 更新或删除要添加事务的支持
     *    **默认执行结束之后，回滚事务
     */
    @Test
    @Transactional //添加事务的支持
    @Rollback(value = false/**/)
    public  void updateCustomer(){
        customerDao.updateCustomer(3l,"黑马程序员");
    }

}
