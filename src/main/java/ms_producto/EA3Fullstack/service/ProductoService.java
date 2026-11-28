package ms_producto.EA3Fullstack.service;

import lombok.RequiredArgsConstructor;
import ms_producto.EA3Fullstack.dto.ProductoRequest;
import ms_producto.EA3Fullstack.dto.ProductoResponse;
import ms_producto.EA3Fullstack.model.Producto;
import ms_producto.EA3Fullstack.repository.ProductoRepository;
import ms_producto.EA3Fullstack.exception.ProductoDuplicadoException;
import ms_producto.EA3Fullstack.exception.ProductoNotFoundException;
import ms_producto.EA3Fullstack.exception.DatosInvalidosException;
import ms_producto.EA3Fullstack.model.FotoP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Value("${app.gateway-base-url:http://localhost:8081/api}")
    private String gatewayUrl;

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    private String normalizar(String valor) {
        if (valor == null) return "";
        return valor.strip()
                .toLowerCase(Locale.ROOT)
                .replaceAll("[\\p{Punct}]", " ")
                .replaceAll("\\s+", " ");
    }

    private String imageUrl(String filename) {
        String base = gatewayUrl == null ? "http://localhost:8081/api" : gatewayUrl.replaceAll("/$", "");
        return base + "/products/images/" + filename;
    }

    private ProductoResponse toResponse(Producto prod) {
        List<String> urls = new ArrayList<>();
        if (prod.getFotos() != null) {
            prod.getFotos().stream()
                    .sorted(Comparator.comparing(FotoP::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                    .map(FotoP::getNombreArchivo)
                    .filter(name -> name != null && !name.isBlank())
                    .map(this::imageUrl)
                    .forEach(urls::add);
        }
        String first = urls.isEmpty() ? null : urls.get(0);
        return ProductoResponse.builder()
                .code(prod.getCodigo()).name(prod.getNombre()).category(prod.getCategoria())
                .brand(prod.getMarca()).price(prod.getPrecioVenta()).stock(prod.getStock())
                .provider(prod.getProvider()).imageUrl(first).imageUrls(urls)
                .subcategory(prod.getSubCategoria()).minStock(prod.getStockMinimo())
                .discountPercent(prod.getDiscountPercent()).description(prod.getDescription())
                .codigo(prod.getCodigo()).categoria(prod.getCategoria()).subCategoria(prod.getSubCategoria())
                .nombre(prod.getNombre()).marca(prod.getMarca()).unidad(prod.getUnidad())
                .precioCompra(prod.getPrecioCompra()).precioVenta(prod.getPrecioVenta())
                .stockMinimo(prod.getStockMinimo()).estadoProducto(prod.getStock() > 0)
                .build();
    }

    private Producto buscarProducto(String codigo) {
        return productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado: " + codigo));

    }

    private void validar(ProductoRequest request) {
        if (request.getCodigo() == null || request.getCodigo().isBlank()) {
            throw new DatosInvalidosException("El codigo es obligatorio");
        }
        if (request.getCategoria() == null || request.getCategoria().isBlank()) {
            throw new DatosInvalidosException("La categoría es obligatoria");
        }
        if (request.getNombre() == null || request.getNombre().trim().length() < 3) {
            throw new DatosInvalidosException("El nombre debe tener al menos 3 caracteres");
        }
        if (request.getMarca() == null || request.getMarca().isBlank()) {
            throw new DatosInvalidosException("La marca es obligatoria");
        }
        if (request.getPrecioCompra() == null || request.getPrecioCompra() <= 0) {
            throw new DatosInvalidosException("El precio de compra debe ser positivo");
        }
        if (request.getPrecioVenta() == null || request.getPrecioVenta() <= 0) {
            throw new DatosInvalidosException("El precio de venta debe ser positivo");
        }
        if (request.getStock() == null || request.getStock() < 0) {
            throw new DatosInvalidosException("El stock no puede ser negativo");
        }
        if (request.getStockMinimo() == null || request.getStockMinimo() < 0) {
            throw new DatosInvalidosException("El stock mínimo no puede ser negativo");
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
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado: " + codigo));
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
            throw new ProductoDuplicadoException("El codigo ya existe");
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

    public ProductoResponse crearProductoMultipart(String code, String name, String category, String brand,
            String provider, Double price, Integer stock, String subcategory, Integer minStock,
            Double discountPercent, String description, List<MultipartFile> photos) {
        if (code == null || code.isBlank()) throw new DatosInvalidosException("El código es obligatorio");
        if (name == null || name.trim().length() < 3) throw new DatosInvalidosException("El nombre debe tener al menos 3 caracteres");
        if (category == null || category.isBlank()) throw new DatosInvalidosException("La categoría es obligatoria");
        if (brand == null || brand.isBlank()) throw new DatosInvalidosException("La marca es obligatoria");
        if (price == null || price <= 0) throw new DatosInvalidosException("El precio debe ser positivo");
        if (stock == null || stock < 0) throw new DatosInvalidosException("El stock no puede ser negativo");
        if (photos == null || photos.isEmpty()) throw new DatosInvalidosException("Debe enviar al menos una foto");
        if (photos.size() > 5) throw new DatosInvalidosException("Debe enviar como máximo 5 fotos");
        if (productoRepository.existsByCodigo(code.strip())) throw new ProductoDuplicadoException("El codigo ya existe");

        Producto producto = new Producto();
        producto.setCodigo(code.strip());
        producto.setNombre(name.trim());
        producto.setCategoria(category.trim());
        producto.setMarca(brand.trim());
        producto.setProvider(provider);
        producto.setSubCategoria(subcategory);
        producto.setUnidad("Unidad");
        producto.setPrecioCompra(price);
        producto.setPrecioVenta(price);
        producto.setStock(stock);
        producto.setStockMinimo(minStock == null ? 0 : minStock);
        producto.setDiscountPercent(discountPercent);
        producto.setDescription(description);
        producto.setFotos(new ArrayList<>());

        try {
            Path directory = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(directory);
            for (int i = 0; i < photos.size(); i++) {
                MultipartFile photo = photos.get(i);
                if (photo.isEmpty()) throw new DatosInvalidosException("Una de las fotos está vacía");
                String contentType = photo.getContentType();
                if (contentType == null || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
                    throw new DatosInvalidosException("Solo se permiten archivos de imagen");
                }
                String original = Paths.get(photo.getOriginalFilename() == null ? "photo" : photo.getOriginalFilename())
                        .getFileName().toString().replaceAll("[^A-Za-z0-9._-]", "_");
                String extension = original.contains(".") ? original.substring(original.lastIndexOf('.')) : ".jpg";
                String safeCode = code.strip().replaceAll("[^A-Za-z0-9_-]", "_");
                String filename = safeCode + "-" + UUID.randomUUID().toString().substring(0, 8)
                        + "-" + (i + 1) + extension.toLowerCase(Locale.ROOT);
                Files.copy(photo.getInputStream(), directory.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                FotoP foto = new FotoP();
                foto.setProducto(producto);
                foto.setNombreArchivo(filename);
                foto.setUrl(imageUrl(filename));
                producto.getFotos().add(foto);
            }
        } catch (IOException ex) {
            throw new DatosInvalidosException("No se pudieron guardar las imágenes");
        }
        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse cambiarPrecio(String codigo, Double price) {
        if (price == null || price <= 0) throw new DatosInvalidosException("El precio debe ser positivo");
        Producto producto = buscarProducto(codigo);
        producto.setPrecioVenta(price);
        producto.setPrecioCompra(producto.getPrecioCompra() == null ? price : producto.getPrecioCompra());
        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse editarProducto(String codigo, ProductoRequest request) {
        Producto producto = buscarProducto(codigo);
        validar(request);

        if (!producto.getCodigo().equalsIgnoreCase(request.getCodigo())
                && productoRepository.existsByCodigo(request.getCodigo())) {
            throw new ProductoDuplicadoException("El codigo ya esta registrado");
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
        if (producto.getFotos() != null) {
            Path directory = Paths.get(uploadDir).toAbsolutePath().normalize();
            producto.getFotos().stream()
                    .map(FotoP::getNombreArchivo)
                    .filter(name -> name != null && !name.isBlank())
                    .forEach(name -> {
                        try { Files.deleteIfExists(directory.resolve(name)); } catch (IOException ignored) { }
                    });
        }
        productoRepository.delete(producto);
    }

    // Delta significa un cambio del stock
    public ProductoResponse ajustarStock(String codigo, Integer delta) {
        if (delta == null || delta == 0) {
            throw new DatosInvalidosException("La cantidad debe ser distinta de 0");
        }

        Producto producto = buscarProducto(codigo);
        int nuevoStock = producto.getStock() + delta;

        if (nuevoStock < 0) {
            throw new DatosInvalidosException("El stock no puede quedar negativo");
        }

        producto.setStock(nuevoStock);
        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse descontarStock(String codigo, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new DatosInvalidosException("La cantidad debe ser mayor a 0");
        }

        Producto producto = buscarProducto(codigo);

        if (producto.getStock() < cantidad) {
            throw new DatosInvalidosException("No hay stock suficiente");
        }

        producto.setStock(producto.getStock() - cantidad);
        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse reponerStock(String codigo, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new DatosInvalidosException("La cantidad debe ser mayor que 0");
        }

        Producto producto = buscarProducto(codigo);
        producto.setStock(producto.getStock() + cantidad);
        return toResponse(productoRepository.save(producto));
    }

}









