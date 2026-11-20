package ms_producto.EA3Fullstack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {
    private String codigo;
    private String categoria;
    private String subCategoria;
    private String nombre;
    private String marca;
    private String unidad;
    private Double precioCompra;
    private Double precioVenta;
    private Integer stock;
    private Integer stockMinimo;
    private boolean estadoProducto;
}
