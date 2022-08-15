package br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.models.Product;
import org.springframework.data.jpa.repository.Query;

public interface ProductDAO extends JpaRepository<Product, Integer> {


	@Query(
			value = "SELECT * FROM product u WHERE u.salesman_id = ?1",
			nativeQuery = true)
	Product findProductBySalesman(Integer status);


}
