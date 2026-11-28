package ms_producto.EA3Fullstack.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {
    private String code;
    private String name;
    private String category;
    private String brand;
    private Double price;
    private Integer stock;
    private String provider;
    private String imageUrl;
    private List<String> imageUrls;
    private String subcategory;
    private Integer minStock;
    private Double discountPercent;
    private String description;

    // Compatibilidad con consumidores antiguos.
    private String codigo;
    private String categoria;
    private String subCategoria;
    private String nombre;
    private String marca;
    private String unidad;
    private Double precioCompra;
    private Double precioVenta;
    private Integer stockMinimo;
    private boolean estadoProducto;
}
