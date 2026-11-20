package ms_producto.EA3Fullstack.service;

import lombok.RequiredArgsConstructor;
import ms_producto.EA3Fullstack.dto.ProductoRequest;
import ms_producto.EA3Fullstack.dto.ProductoResponse;
import ms_producto.EA3Fullstack.model.Producto;
import ms_producto.EA3Fullstack.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    private String normalizar(String valor) {
        if (valor == null) return "";
        return valor.strip()
                .toLowerCase(Locale.ROOT)
                .replaceAll("[\\p{Punct}]", " ")
                .replaceAll("\\s+", " ");
    }

    private ProductoResponse toResponse(Producto prod) {
        return new ProductoResponse(
                prod.getCodigo(),
                prod.getCategoria(),
                prod.getSubCategoria(),
                prod.getNombre(),
                prod.getMarca(),
                prod.getUnidad(),
                prod.getPrecioCompra(),
                prod.getPrecioVenta(),
                prod.getStock(),
                prod.getStockMinimo(),
                prod.getStock() > 0
        );

    }

    private Producto buscarProducto(String codigo) {
        return productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + codigo));

    }

    private void validar(ProductoRequest request) {
        if (request.getCodigo() == null || request.getCodigo().isBlank()) {
            throw new RuntimeException("El codigo es obligatorio");
        }
        if (request.getCategoria() == null || request.getCategoria().isBlank()) {
            throw new RuntimeException("La categoría es obligatoria");
        }
        if (request.getNombre() == null || request.getNombre().trim().length() < 3) {
            throw new RuntimeException("El nombre debe tener al menos 3 caracteres");
        }
        if (request.getMarca() == null || request.getMarca().isBlank()) {
            throw new RuntimeException("La marca es obligatoria");
        }
        if (request.getPrecioCompra() == null || request.getPrecioCompra() <= 0) {
            throw new RuntimeException("El precio de compra debe ser positivo");
        }
        if (request.getPrecioVenta() == null || request.getPrecioVenta() <= 0) {
            throw new RuntimeException("El precio de venta debe ser positivo");
        }
        if (request.getStock() == null || request.getStock() < 0) {
            throw new RuntimeException("El stock no puede ser negativo");
        }
        if (request.getStockMinimo() == null || request.getStockMinimo() < 0) {
            throw new RuntimeException("El stock mínimo no puede ser negativo");
        }

    }

    // Nuevo requerimiento 3: operaciones reales del catálogo público.
    // === CATÁLOGO ===
    public List<ProductoResponse> listarCatalogo(String texto, String categoria, String disponible, Double maxPrecio) {
        String q = normalizar(texto);
        return productoRepository.findAll()
                .stream()
                .filter(prod -> q.isEmpty() || (
                        normalizar(prod.getCodigo()).contains(q) ||
                                normalizar(prod.getNombre()).contains(q) ||
                                normalizar(prod.getMarca()).contains(q) ||
                                normalizar(prod.getCategoria()).contains(q) ||
                                normalizar(prod.getSubCategoria()).contains(q)
                ))
                .filter(prod -> categoria == null || categoria.isEmpty() || prod.getCategoria().equalsIgnoreCase(categoria))
                .filter(prod -> disponible == null || disponible.isBlank()
                        || ("disponible".equalsIgnoreCase(disponible) && prod.getStock() > 0)
                        || ("agotado".equalsIgnoreCase(disponible) && prod.getStock() == 0))
                .filter(prod -> maxPrecio == null || prod.getPrecioVenta() <= maxPrecio)
                .sorted(Comparator.comparing(Producto::getCodigo))
                .map(this::toResponse)
                .toList();
    }

    // Detalle por codigo
    public ProductoResponse obtenerPorCodigo(String codigo) {
        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + codigo));
        return toResponse(producto);
    }

    public List<String> listarCategorias() {
        return productoRepository.findAll()
                .stream()
                .map(Producto::getCategoria)
                .filter(cat -> cat != null && !cat.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    //operaciones administrativas para el panel React.
    // == ADMIN PRODUCTO ==

    public ProductoResponse crearProducto(ProductoRequest request) {
        validar(request);

        if (productoRepository.existsByCodigo(request.getCodigo())) {
            throw new RuntimeException("El codigo ya existe");
        }

        Producto producto = new Producto();
        producto.setCodigo(request.getCodigo().strip());
        producto.setCategoria(request.getCategoria().trim());
        producto.setSubCategoria(request.getSubCategoria() == null ? "" : request.getSubCategoria().trim());
        producto.setNombre(request.getNombre().trim());
        producto.setMarca(request.getMarca());
        producto.setUnidad(request.getUnidad().trim());
        producto.setPrecioCompra(request.getPrecioCompra());
        producto.setPrecioVenta(request.getPrecioVenta());
        producto.setStock(request.getStock());
        producto.setStockMinimo(request.getStockMinimo());

        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse editarProducto(String codigo, ProductoRequest request) {
        Producto producto = buscarProducto(codigo);
        validar(request);

        if (!producto.getCodigo().equalsIgnoreCase(request.getCodigo())
                && productoRepository.existsByCodigo(request.getCodigo())) {
            throw new RuntimeException("El codigo ya esta registrado");
        }

        producto.setCodigo(request.getCodigo().strip());
        producto.setCategoria(request.getCategoria().trim());
        producto.setSubCategoria(request.getSubCategoria() == null ? "" : request.getSubCategoria().trim());
        producto.setNombre(request.getNombre().trim());
        producto.setMarca(request.getMarca());
        producto.setUnidad(request.getUnidad().trim());
        producto.setPrecioCompra(request.getPrecioCompra());
        producto.setPrecioVenta(request.getPrecioVenta());
        producto.setStock(request.getStock());
        producto.setStockMinimo(request.getStockMinimo());
        return toResponse(productoRepository.save(producto));

    }

    public void eliminarProducto(String codigo) {
        Producto producto = buscarProducto(codigo);
        productoRepository.delete(producto);
    }

    // Delta significa un cambio del stock
    public ProductoResponse ajustarStock(String codigo, Integer delta) {
        if (delta == null || delta == 0) {
            throw new RuntimeException("La cantidad debe ser distinta de 0");
        }

        Producto producto = buscarProducto(codigo);
        int nuevoStock = producto.getStock() + delta;

        if (nuevoStock < 0) {
            throw new RuntimeException("El stock no puede quedar negativo");
        }

        producto.setStock(nuevoStock);
        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse descontarStock(String codigo, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        Producto producto = buscarProducto(codigo);

        if (producto.getStock() < cantidad) {
            throw new RuntimeException("No hay stock suficiente");
        }

        producto.setStock(producto.getStock() - cantidad);
        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse reponerStock(String codigo, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor que 0");
        }

        Producto producto = buscarProducto(codigo);
        producto.setStock(producto.getStock() + cantidad);
        return toResponse(productoRepository.save(producto));
    }

}









