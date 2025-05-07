package co.apexglobal.ordersmng.domain.entity;

import lombok.Data;

@Data
public class OrdenEvent {
	private String type;
	private Orden orden;
}
