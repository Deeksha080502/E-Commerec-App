package com.ecommerce.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.domain.RequestInventoryEntity;

public interface InventoryRepository extends JpaRepository<RequestInventoryEntity, Long> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE tb_inventory "
			+ "SET quantity = :quantity WHERE productid = :productid", nativeQuery = true)
	int updateInventory(@Param("productid") String productID, @Param("quantity") int quantity);

	RequestInventoryEntity findByProductID(String productID);

	boolean existsByProductID(String productID);

	Long deleteByProductID(String productID);

	@Transactional
	@Modifying
	@Query(value = "delete from TB_inventory i where i.productid = ?1", nativeQuery = true)
	boolean deleteProductByProductID(String ProductID);

//	 void deleteById(String id);

}

