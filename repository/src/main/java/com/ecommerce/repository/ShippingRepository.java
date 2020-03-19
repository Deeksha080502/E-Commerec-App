package com.ecommerce.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.domain.RequestShippingEntity;

public interface ShippingRepository extends JpaRepository<RequestShippingEntity, Long> {

	/*
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query(value = "DROP TABLE tb_shipping", nativeQuery = true) void
	 * droptable();
	 */

	RequestShippingEntity findByOrderID(String orderid);

	@Transactional
	@Modifying
	@Query(value = "UPDATE tb_shipping "
			+ "SET delivery_status = :delivery_status WHERE orderid = :orderid", nativeQuery = true)
	int updateDeliveryStatus(@Param("orderid") String productID, @Param("delivery_status") String deliveryStatus);

	@Transactional
	@Modifying
	@Query(value = "UPDATE tb_shipping "
			+ "SET delivery_date = :delivery_date WHERE orderid = :orderid", nativeQuery = true)
	int updateDeliveryDate(@Param("orderid") String orderID, @Param("delivery_date") String deliveryDate);

	boolean existsByOrderID(String orderid);

	@Transactional
	@Modifying
	@Query(value = "delete from TB_shipping i where i.orderid = ?1", nativeQuery = true)
	int deleteProductByOrderId(String orderId);

}
