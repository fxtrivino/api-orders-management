package co.apexglobal.ordersmng.service.impl;

import java.util.Date;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import co.apexglobal.ordersmng.domain.dto.PageResponseDTO;
import co.apexglobal.ordersmng.domain.entity.Orden;
import co.apexglobal.ordersmng.domain.entity.Producto;
import co.apexglobal.ordersmng.exception.ProductNotFoundException;
import co.apexglobal.ordersmng.repository.OrdenRepository;
import co.apexglobal.ordersmng.service.OrdenService;

/**
 * Servicio para manejar operaciones relacionadas con órdenes.
 */

@Service
public class OrdenServiceImpl implements OrdenService {

	private OrdenRepository ordenRepository;

	public OrdenServiceImpl(OrdenRepository ordenRepository) {
		this.ordenRepository = ordenRepository;
	}

	@CacheEvict(value = "ordenes", allEntries = true)
	@Override
	public Orden createOrden(Orden orden) {
		float total = 0;
		float subtotal;

		if (orden.getItems() == null || orden.getItems().size() <= 0) {
			throw new ProductNotFoundException("Los items deben contener al menos un producto");
		}

		for (Producto producto : orden.getItems()) {
			subtotal = producto.getCantidad() * producto.getPrecioUnitario();
			total += subtotal;
		}

		orden.setFechaCreacion(new Date());
		orden.setTotal(total);

		Orden ordenSaved = ordenRepository.save(orden);
		return ordenSaved;
	}

	@Cacheable(value = "ordenes", key = "#idUsuario != null ? #idUsuario + '-' + #pageable.pageNumber + '-' + #pageable.pageSize : 'all-' + #pageable.pageNumber + '-' + #pageable.pageSize")
	@Override
	public PageResponseDTO<Orden> getOrdenByIdUsuarioPageable(String idUsuario, Pageable pageable) {

		Page<Orden> page = (idUsuario == null || idUsuario.isEmpty()) ? ordenRepository.findAll(pageable)
				: ordenRepository.findByIdUsuario(idUsuario, pageable);

		PageResponseDTO<Orden> dto = new PageResponseDTO<>();
		dto.setContent(page.getContent());
		dto.setPage(page.getNumber());
		dto.setSize(page.getSize());
		dto.setTotalElements(page.getTotalElements());
		dto.setTotalPages(page.getTotalPages());

		return dto;

	}

}
