package co.apexglobal.ordersmng.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import co.apexglobal.ordersmng.domain.dto.OrdenDTO;
import co.apexglobal.ordersmng.domain.dto.PageResponseDTO;
import co.apexglobal.ordersmng.domain.dto.ProductoDTO;
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
	public Orden createOrden(OrdenDTO ordenDTO) {
		float total = 0;
		float subtotal;

		if (ordenDTO.getItems() == null || ordenDTO.getItems().size() <= 0) {
			throw new ProductNotFoundException("Los items de la orden deben contener al menos un producto");
		}

		for (ProductoDTO producto : ordenDTO.getItems()) {
			subtotal = producto.getCantidad() * producto.getPrecioUnitario();
			total += subtotal;
		}
		
		// Se copia todo el objeto OrdenDTO a Orden
		Orden orden = new Orden();
		orden.setIdUsuario(ordenDTO.getIdUsuario());
		
		// Se copia el List de ProductoDTO a un list de Producto
		List<Producto> listProductos = ordenDTO.getItems().stream()
			    .map(p -> new Producto(p.getIdProducto(), p.getCantidad(), p.getPrecioUnitario())) // o producto.clone()
			    .collect(Collectors.toList());

		orden.setItems(listProductos);	
		
		// Se incluye la fecha de creación 
		orden.setFechaCreacion(new Date());
		
		// Se incluye el total calculado, sumatoria de cantidad * precioUnitario de todos los productos ó items
		orden.setTotal(total);

		Orden ordenCreated = ordenRepository.save(orden);
		return ordenCreated;
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
