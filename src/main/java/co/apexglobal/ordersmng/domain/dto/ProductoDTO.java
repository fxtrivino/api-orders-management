package co.apexglobal.ordersmng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
	
	private String idProducto;
	private int cantidad;
	private float precioUnitario;
}
