package co.apexglobal.ordersmng.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenDTO {
	
	private String idUsuario;	
	private List<ProductoDTO> items;	
}
