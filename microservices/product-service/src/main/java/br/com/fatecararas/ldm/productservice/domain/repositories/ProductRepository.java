package br.com.fatecararas.ldm.productservice.domain.repositories;

import br.com.fatecararas.ldm.productservice.domain.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findByDescriptionContains(String description);

    Optional<ProductEntity> findByBarcode(String barcode);
}
