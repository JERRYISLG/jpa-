package cn.itcast.dao;

import cn.itcast.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerDao extends JpaRepository<Customer,Long> ,JpaSpecificationExecutor<Customer> {

    @Query(value = "from Customer")
    public List<Customer> findAllCustomer();

    @Query(value = "from Customer where custName=? ")
    public Customer findCustomer(String custName);

    @Query(value = "from Customer where custName=? and custId=?")
    public Customer findCustomer2(String custName,Long custId);

    @Query(value = "update Customer set custName= ?2 where custId= ?1")
    @Modifying
    public void updateCustomer(Long custId,String custName );
}
