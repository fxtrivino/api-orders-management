package co.apexglobal.ordersmng.service;

import org.springframework.data.domain.Pageable;
import co.apexglobal.ordersmng.domain.dto.PageResponseDTO;
import co.apexglobal.ordersmng.domain.entity.Orden;

public interface OrdenService {
	public Orden createOrden(Orden orden);
	public PageResponseDTO<Orden> getOrdenByIdUsuarioPageable(String idUsuario, Pageable pageable);
}
