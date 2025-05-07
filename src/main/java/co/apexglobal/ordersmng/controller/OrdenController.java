package co.apexglobal.ordersmng.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.apexglobal.ordersmng.domain.dto.PageResponseDTO;
import co.apexglobal.ordersmng.domain.entity.Orden;
import co.apexglobal.ordersmng.domain.entity.OrdenEvent;
import co.apexglobal.ordersmng.service.OrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controllador que recibe peticiones HTTP para las operaciones relacionadas con
 * órdenes.
 */
@RestController
@RequestMapping("/ordenes")
@Tag(name = "Órdenes", description = "Operaciones relacionadas con órdenes de productos")
public class OrdenController {

	private OrdenService ordenService;
	private KafkaTemplate<String, OrdenEvent> kafkaTemplate;

	private static final String TOPIC = "ordenes_creadas";

	public OrdenController(OrdenService ordenService, KafkaTemplate<String, OrdenEvent> kafkaTemplate) {
		this.ordenService = ordenService;
		this.kafkaTemplate = kafkaTemplate;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Crear una orden", description = "Crea una nueva orden y la publica en Kafka")
	public ResponseEntity<Orden> createOrden(@RequestBody Orden orden) {
		Orden ordenCreated = ordenService.createOrden(orden);

		OrdenEvent event = new OrdenEvent();
		event.setOrden(ordenCreated);
		event.setType("ORDEN_CREADA");
		kafkaTemplate.send(TOPIC, event);

		return new ResponseEntity<>(ordenCreated, HttpStatus.CREATED);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Obtener todas las órdenes", description = "Devuelve una lista paginada de órdenes")
	public ResponseEntity<PageResponseDTO<Orden>> getOrdenByIdUsuarioPageable(@PageableDefault(size = 10, page = 0) Pageable pageable, 
																		  @RequestParam(required = false) String idUsuario) {
		PageResponseDTO<Orden> listOrdenes = ordenService.getOrdenByIdUsuarioPageable(idUsuario, pageable);
		return new ResponseEntity<>(listOrdenes, HttpStatus.OK);
	}
}
