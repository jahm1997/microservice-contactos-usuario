package com.product.productmicroservice.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.product.productmicroservice.Entity.ContactEntity;
import com.product.productmicroservice.Repository.ContactRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/contact")
public class ContactController {

	@Autowired
	private ContactRepository contactRepo;

	@GetMapping
	public ResponseEntity<List<ContactEntity>> getAllContacts() {
		List<ContactEntity> contactEntities = contactRepo.findAll();
		ResponseEntity<List<ContactEntity>> responseEntity = new ResponseEntity<>(contactEntities, HttpStatus.OK);
		System.out.println(responseEntity);
		return responseEntity;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContactEntity> getContact(@PathVariable("id") String id) {
		ContactEntity response = contactRepo.findById(id).orElse(null);
		if (response == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void createContact(@RequestBody ContactEntity contactEntity) {
		contactRepo.save(contactEntity);
	}

	@GetMapping("/usuario/{id}")
	public List<ContactEntity> getByUser(@PathVariable("id") String id) {
		return contactRepo.findByUsuarioId(id);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ContactEntity> updateContactPartially(@PathVariable("id") String id,
			@RequestBody ContactEntity updatedContact) {
		ContactEntity existingContact = contactRepo.findById(id).orElse(null);
		if (existingContact == null) {
			return ResponseEntity.notFound().build();
		}

		if (updatedContact.getNombres() != null) {
			existingContact.setNombres(updatedContact.getNombres());
		}
		if (updatedContact.getApellidos() != null) {
			existingContact.setApellidos(updatedContact.getApellidos());
		}
		if (updatedContact.getTelefono() != null) {
			existingContact.setTelefono(updatedContact.getTelefono());
		}
		if (updatedContact.getTipoContacto() != null) {
			existingContact.setTipoContacto(updatedContact.getTipoContacto());
		}
		
		if (updatedContact.getComentarios() != null) {
			existingContact.setComentarios(updatedContact.getComentarios());
		}
		
		if (updatedContact.getTerminado() != null) {
			existingContact.setTerminado(updatedContact.getTerminado());
		}

		ContactEntity updatedEntity = contactRepo.save(existingContact);

		return ResponseEntity.ok(updatedEntity);
	}

}
