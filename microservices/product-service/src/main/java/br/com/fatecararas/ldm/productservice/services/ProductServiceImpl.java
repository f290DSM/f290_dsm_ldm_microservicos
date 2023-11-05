package br.com.fatecararas.ldm.productservice.services;

import br.com.fatecararas.api.ProductService;
import br.com.fatecararas.api.core.dto.Product;
import br.com.fatecararas.ldm.productservice.domain.Cambio;
import br.com.fatecararas.ldm.productservice.domain.entities.ProductEntity;
import br.com.fatecararas.ldm.productservice.domain.repositories.ProductRepository;
import br.com.fatecararas.ldm.productservice.proxy.CambioProxy;
import br.com.fatecararas.util.http.exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final CambioProxy proxy;
    private final ModelMapper mapper;

    public ProductServiceImpl(ProductRepository repository, CambioProxy proxy, ModelMapper mapper) {
        this.repository = repository;
        this.proxy = proxy;
        this.mapper = mapper;
    }

    @GetMapping("/paginated/all")
    public ResponseEntity<Page<ProductEntity>> getAll(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "100") int size) {

        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.ASC, "description");

        Page<ProductEntity> products = repository.findAll(pageable);

        if (products.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();

        return ResponseEntity.ok().body(products);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody ProductEntity productEntity) {

        ProductEntity p = repository.save(productEntity);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/find/{term}")
    public List<ProductEntity> findByDescription(@PathVariable String term) {
        return repository.findByDescriptionContains(term);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Integer id) throws Exception{
        Optional<ProductEntity> optional = repository.findById(id);
        if(optional.isEmpty()) throw new NotFoundException("Product not found. Id: "+id);
        return mapper.map(optional.get(), Product.class);
    }

    @DeleteMapping("/remove/{id}")
    public void deleteById(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody ProductEntity productEntity) {
        Optional<ProductEntity> optional = repository.findById(productEntity.getId());

        if (optional.isEmpty())
            return ResponseEntity.notFound().build();

        ProductEntity updatedProductEntity = optional.get();
        updatedProductEntity.setBarcode(productEntity.getBarcode());
        updatedProductEntity.setDescription(productEntity.getDescription());
        updatedProductEntity.setPrice(productEntity.getPrice());

        return ResponseEntity.ok().body(updatedProductEntity);
    }

    @GetMapping("/export/{barcode}")
    public ResponseEntity<?> findByBarcode(@PathVariable("barcode") String barcode) {
        Optional<ProductEntity> optional = repository.findByBarcode(barcode);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ProductEntity productEntity = optional.get();

        Cambio cambio = proxy.getCambio(productEntity.getPrice());
        productEntity.setPrice(cambio.getValue());

        return ResponseEntity.ok().body(productEntity);

    }

// TODO: Criar um end-point para recuperar um produto por codigo de barras
// TODO: Criar um end-point para excluir um produto pelo codigo de barras

// TODO: Criar uma collection no Postman ou similar para testar as requisições
}
