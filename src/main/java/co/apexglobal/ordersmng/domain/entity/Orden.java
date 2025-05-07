package co.apexglobal.ordersmng.domain.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orden")
public class Orden {
	
	@Id
	private String idUsuario;	
	private List<Producto> items;	
	private Date fechaCreacion;
	private float total;
}
