package co.apexglobal.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import co.apexglobal.ordersmng.domain.dto.PageResponseDTO;
import co.apexglobal.ordersmng.domain.entity.Orden;
import co.apexglobal.ordersmng.domain.entity.Producto;
import co.apexglobal.ordersmng.exception.ProductNotFoundException;
import co.apexglobal.ordersmng.repository.OrdenRepository;
import co.apexglobal.ordersmng.service.impl.OrdenServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrdenServiceImplTest {

    @Mock
    private OrdenRepository ordenRepository;

    @InjectMocks
    private OrdenServiceImpl ordenService;

    @Test
    void testCreateOrdenSuccess() {
        Producto producto1 = new Producto("P1", 2, 100.0f);
        Producto producto2 = new Producto("P2", 1, 50.0f);
        Orden orden = new Orden();
        orden.setItems(List.of(producto1, producto2));

        when(ordenRepository.save(any(Orden.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Orden result = ordenService.createOrden(orden);

        assertNotNull(result.getFechaCreacion());
        assertEquals(250.0f, result.getTotal());
        verify(ordenRepository).save(orden);
    }

    @Test
    void testCreateOrdenWithoutItemsThrowsException() {
        Orden orden = new Orden();
        orden.setItems(Collections.emptyList());

        assertThrows(ProductNotFoundException.class, () -> ordenService.createOrden(orden));
    }

    @Test
    void testGetOrdenByIdUsuarioPageable_WithIdUsuario() {
        String idUsuario = "user1";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Orden> mockedPage = new PageImpl<>(List.of(new Orden()), pageable, 1);

        when(ordenRepository.findByIdUsuario(eq(idUsuario), eq(pageable))).thenReturn(mockedPage);

        PageResponseDTO<?> response = ordenService.getOrdenByIdUsuarioPageable(idUsuario, pageable);

        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getContent().size());
        verify(ordenRepository).findByIdUsuario(idUsuario, pageable);
    }

    @Test
    void testGetOrdenByIdUsuarioPageable_WithoutIdUsuario() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Orden> mockedPage = new PageImpl<>(List.of(new Orden()), pageable, 1);

        when(ordenRepository.findAll(eq(pageable))).thenReturn(mockedPage);

        PageResponseDTO<?> response = ordenService.getOrdenByIdUsuarioPageable(null, pageable);

        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getContent().size());
        verify(ordenRepository).findAll(pageable);
    }
}

