package br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.services;

import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.models.Ordered_Items;
import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.models.Product;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.repositories.Ordered_ItemsDAO;
import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.repositories.ProductDAO;

import java.util.List;
import java.util.Optional;

@Service
public class Ordered_ItemsService {

	@Autowired
	private Ordered_ItemsDAO ordered_ItemsDAO;

	@Autowired
	private ProductDAO productDAO;

	public List<Ordered_Items> allItems() {
		List<Ordered_Items> list = ordered_ItemsDAO.findAll();

		return list;
	}

	public Product findById(Integer id) {
		Optional<Product> product = productDAO.findById(id);
		return product.get();
	}

	public Product findBySalesman(Integer id) {
		return productDAO.findProductBySalesman(id);
	}

}
