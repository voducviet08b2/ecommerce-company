package com.example.ecommerce.dao;

import com.example.ecommerce.dto.ProductDTONew;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductDAO {

  @PersistenceContext
  private EntityManager entityManager;

  public List<ProductDTONew> getAllDto() {
    StringBuilder sql = new StringBuilder();
    sql.append("select p.id as id, p.product_name AS productName, p.price as price,c.category_name AS categoryName from product p inner join category c on p.category_id = c.id");
    Query query = entityManager.createNativeQuery(sql.toString(),"ProductDTONew");
    return query.getResultList();
  }
}






