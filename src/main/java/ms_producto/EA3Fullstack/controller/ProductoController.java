package ms_producto.EA3Fullstack.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ms_producto.EA3Fullstack.dto.ProductoRequest;
import ms_producto.EA3Fullstack.dto.ProductoResponse;
import ms_producto.EA3Fullstack.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
// Permite llamadas HTTP desde los puertos usados por el frontend React.
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173", "http://127.0.0.1:5174", "http://127.0.0.1:5175"})
public class ProductoController {

    private final ProductoService productoService;


    // Endpoints públicos del catálogo consumidos por React.


    // Nuevo requerimiento 3: endpoint React de listado con filtros opcionales.
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listarCatalogo(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String disponibilidad,
            @RequestParam(required = false) Double maxPrecio
    ) {
        return ResponseEntity.ok(
                productoService.listarCatalogo(texto, categoria, disponibilidad, maxPrecio)
        );
    }

    // Nuevo requerimiento 3: endpoint React de detalle por código.
    @GetMapping("/{codigo}")
    public ResponseEntity<ProductoResponse> obtenerPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(productoService.obtenerPorCodigo(codigo));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<String>> listarCategorias() {
        return ResponseEntity.ok(productoService.listarCategorias());
    }


    // Endpoints administrativos protegidos por seguridad/JWT.


    // Nuevo requerimiento 3: creación administrativa consumible desde React.
    @PostMapping
    public ResponseEntity<ProductoResponse> crearProducto(@Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.crearProducto(request));
    }

    // Nuevo requerimiento 3: edición administrativa consumible desde React.
    @PutMapping("/{codigo}")
    public ResponseEntity<ProductoResponse> editarProducto(
            @PathVariable String codigo,
            @Valid @RequestBody ProductoRequest request
    ) {
        return ResponseEntity.ok(productoService.editarProducto(codigo, request));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String codigo) {
        productoService.eliminarProducto(codigo);
        return ResponseEntity.noContent().build();
    }

    // Nuevo requerimiento 3: ajuste de stock solicitado por la tienda React.
    @PatchMapping("/{codigo}/stock")
    public ResponseEntity<ProductoResponse> ajustarStock(
            @PathVariable String codigo,
            @RequestParam Integer delta
    ) {
        return ResponseEntity.ok(productoService.ajustarStock(codigo, delta));
    }

    @PatchMapping("/{codigo}/descontar-stock")
    public ResponseEntity<ProductoResponse> descontarStock(
            @PathVariable String codigo,
            @RequestParam Integer cantidad
    ) {
        return ResponseEntity.ok(productoService.descontarStock(codigo, cantidad));
    }

    @PatchMapping("/{codigo}/reponer-stock")
    public ResponseEntity<ProductoResponse> reponerStock(
            @PathVariable String codigo,
            @RequestParam Integer cantidad
    ) {
        return ResponseEntity.ok(productoService.reponerStock(codigo, cantidad));
    }
}

