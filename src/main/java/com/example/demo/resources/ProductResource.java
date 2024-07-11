package com.example.demo.resources;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dtos.ProductDto;
import com.example.demo.model.entities.Product;
import com.example.demo.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/*findAll
 *findById
 *delete
 *save
 *update
 */

@RestController
@RequestMapping(value = "/products")
@Tag(name = "Ações de produto")
public class ProductResource {
	
	@Autowired
	private ProductService productService;

	@GetMapping
	@Operation(description = "Retorna todos os produtos")
	public ResponseEntity<List<Product>> findAll() {
		List<Product> list = productService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	@Operation(description = "Retorna um produto pelo ID")
	public ResponseEntity<Object> findById(@PathVariable Long id){
		Product product = productService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}
	
	@Operation(description = "Deleta um produto pelo ID")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@Operation(description = "Salva um produto")
    public ResponseEntity<Object> save(@RequestBody @Valid ProductDto productDto){
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }
	
	@PutMapping("/{id}")
	@Operation(description = "Atualiza um produto")
    public ResponseEntity<Object> update(@PathVariable(value="id") Long id, @RequestBody @Valid ProductDto productDto){
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(id, product));
	}
}
