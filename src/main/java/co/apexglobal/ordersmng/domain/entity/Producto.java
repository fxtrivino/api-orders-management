package co.apexglobal.ordersmng.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
	
	private String idProducto;
	private int cantidad;
	private float precioUnitario;
}
