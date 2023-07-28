package com.product.productmicroservice.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.product.productmicroservice.Entity.ContactEntity;

public interface ContactRepository extends MongoRepository<ContactEntity, String>{

	List<ContactEntity> findByUsuarioId(String usuarioId);
	
	
}
