package com.app.test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.app.model.Product;
import com.app.repo.ProductRepository;

@Component
public class ProductTest implements CommandLineRunner {
	@Autowired
	private ProductRepository repo;

	@Override
	public void run(String... args) throws Exception {
		System.out.println(repo.getClass().getName());
		// save one by one
		repo.save(new Product(11, "A", 3.3));
		repo.save(new Product(12, "B", 4.3));
		repo.save(new Product(13, "C", 5.3));
		repo.save(new Product(14, "D", 6.3));

		// saveAll
		repo.saveAll(Arrays.asList(new Product(15, "E", 7.3), new Product(16, "F", 8.3), new Product(17, "G", 9.3)));

		// get based on id
		Optional<Product> findById = repo.findById(13);
		if (findById.isPresent()) {
			System.out.println(findById.get());
		} else {
			System.out.println("No Data Found");
		}
		repo.findAll().forEach(System.out::println);
		// update
		repo.save(new Product(17, "H", 9.3));
		repo.save(new Product(18, "I", 10.3));

		// deleteById
		// repo.deleteById(18);
		// delete all one by one
		// repo.deleteAll();
		// Sorting Concept
		Sort sort = Sort.by(Direction.DESC, "prodId");
		// Sort sort=Sort.by(Direction.ASC, "prodId");
		repo.findAll(sort).forEach(System.out::println);
		Pageable pagable = PageRequest.of(0, 3);
		Page<Product> page = repo.findAll(pagable);
		List<Product> content = page.getContent();
		content.stream().sorted((p1, p2) -> p1.getProdId().compareTo(p2.getProdId())).forEach(System.out::println);
		// content.forEach(System.out::println);
		System.out.println(page.isFirst());
		System.out.println(page.isLast());
		System.out.println(page.hasNext());
		System.out.println(page.hasPrevious());
		System.out.println(page.getTotalPages());
		System.out.println(page.getTotalElements());
		System.out.println(page.getSize());
		System.out.println(page.getNumber());
		Product p=new Product();
		p.setProdId(19);
		p.setProdCode("A");
		Example<Product>  example=Example.of(p);
		repo.findAll(example).forEach(System.out::println);

	}

}
