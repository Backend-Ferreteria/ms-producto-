package ms_producto.EA3Fullstack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false, length = 50)
    private String categoria;

    @Column(length = 50)
    private String subCategoria;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String marca;

    // "Saco", "Galón", "Unidad"
    @Column(length = 50)
    private String unidad;

    @Column(name = "precio_compra")
    private Double precioCompra;

    @Column(name = "precio_venta", nullable = false)
    private Double precioVenta;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "stock_minimo")
    private Integer stockMinimo;

    @Column(length = 100)
    private String provider;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(length = 2000)
    private String description;


    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FotoP> fotos;


}
