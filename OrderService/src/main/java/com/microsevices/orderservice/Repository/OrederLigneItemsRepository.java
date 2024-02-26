package com.microsevices.orderservice.Repository;

import com.microsevices.orderservice.Enitiy.OrderLigneItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrederLigneItemsRepository extends JpaRepository<OrderLigneItems,Long> {
}
