package br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.models.Ordered_Items;

public interface Ordered_ItemsDAO extends JpaRepository<Ordered_Items, Integer> {

}
