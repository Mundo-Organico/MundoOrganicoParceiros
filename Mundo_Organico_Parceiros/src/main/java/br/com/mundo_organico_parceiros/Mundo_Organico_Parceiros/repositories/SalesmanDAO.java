package br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.models.Salesman;

public interface SalesmanDAO extends JpaRepository<Salesman, Integer>{

	boolean existsByEmail(String email);
	
	public Salesman findByEmailAndPassword(String email, String password);

	Optional<Salesman> findByEmail(String email);
}
