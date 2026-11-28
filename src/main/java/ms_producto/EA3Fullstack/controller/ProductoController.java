package ms_producto.EA3Fullstack.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import ms_producto.EA3Fullstack.dto.PriceRequest;
import ms_producto.EA3Fullstack.dto.ProductoResponse;
import ms_producto.EA3Fullstack.exception.ProductoNotFoundException;
import ms_producto.EA3Fullstack.service.ProductoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    @GetMapping("/products")
    public List<ProductoResponse> listarCatalogo() {
        return productoService.listarCatalogo(null, null, null, null);
    }

    @GetMapping("/products/{codigo}")
    public ProductoResponse obtenerPorCodigo(@PathVariable String codigo) {
        return productoService.obtenerPorCodigo(codigo);
    }

    @GetMapping("/products/images/{archivo}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String archivo) {
        Path path = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(archivo).normalize();
        if (!path.startsWith(Paths.get(uploadDir).toAbsolutePath().normalize()) || !Files.isRegularFile(path)) {
            throw new ProductoNotFoundException("Imagen no encontrada: " + archivo);
        }
        MediaType contentType = MediaTypeFactory.getMediaType(path.getFileName().toString())
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().contentType(contentType)
                .body(new FileSystemResource(path));
    }

    @PostMapping(value = "/crud/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductoResponse> crearProducto(
            @RequestParam String code,
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam String brand,
            @RequestParam(required = false) String provider,
            @RequestParam Double price,
            @RequestParam Integer stock,
            @RequestParam(required = false) String subcategory,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) Double discountPercent,
            @RequestParam(required = false) String description,
            @RequestParam("photos") List<MultipartFile> photos) {
        ProductoResponse response = productoService.crearProductoMultipart(code, name, category, brand,
                provider, price, stock, subcategory, minStock, discountPercent, description, photos);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/crud/products/{codigo}/price")
    public ProductoResponse cambiarPrecio(@PathVariable String codigo,
                                         @Valid @RequestBody PriceRequest body) {
        return productoService.cambiarPrecio(codigo, body.getPrice());
    }

    @DeleteMapping("/crud/products/{codigo}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String codigo) {
        productoService.eliminarProducto(codigo);
        return ResponseEntity.noContent().build();
    }

    // Endpoints intocables usados por pagos.
    @PatchMapping("/productos/{codigo}/stock")
    public ProductoResponse ajustarStockOriginal(@PathVariable String codigo, @RequestParam Integer delta) {
        return productoService.ajustarStock(codigo, delta);
    }

    @PatchMapping("/productos/{codigo}/descontar-stock")
    public ProductoResponse descontarStockOriginal(@PathVariable String codigo, @RequestParam Integer cantidad) {
        return productoService.descontarStock(codigo, cantidad);
    }

    @PatchMapping("/productos/{codigo}/reponer-stock")
    public ProductoResponse reponerStockOriginal(@PathVariable String codigo, @RequestParam Integer cantidad) {
        return productoService.reponerStock(codigo, cantidad);
    }

    @PatchMapping("/crud/products/{codigo}/ajustar-stock")
    public ProductoResponse ajustarStock(@PathVariable String codigo, @RequestParam Integer delta) {
        return productoService.ajustarStock(codigo, delta);
    }

    @PatchMapping("/products/{codigo}/descontar-stock")
    public ProductoResponse descontarStock(@PathVariable String codigo, @RequestParam Integer cantidad) {
        return productoService.descontarStock(codigo, cantidad);
    }

    @PatchMapping("/products/{codigo}/reponer-stock")
    public ProductoResponse reponerStock(@PathVariable String codigo, @RequestParam Integer cantidad) {
        return productoService.reponerStock(codigo, cantidad);
    }
}

