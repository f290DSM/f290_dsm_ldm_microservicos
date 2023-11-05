package br.com.fatecararas.api;

import br.com.fatecararas.api.core.dto.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductService {
    @GetMapping("/{id}")
    Product findById(@PathVariable Integer id) throws Exception;
}
