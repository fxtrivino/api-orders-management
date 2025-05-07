package co.apexglobal.ordersmng.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import co.apexglobal.ordersmng.domain.entity.Orden;

public interface OrdenRepository extends MongoRepository<Orden, String>{

	public Optional<Orden> findByIdUsuario(String idUsuario);
	public Page<Orden> findByIdUsuario(String idUsuario, Pageable pageable);
}
