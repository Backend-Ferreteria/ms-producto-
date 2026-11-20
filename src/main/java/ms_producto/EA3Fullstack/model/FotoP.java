package ms_producto.EA3Fullstack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "foto_p", indexes = {
        @Index(name = "idx_codigo_asociado", columnList = "producto_codigo")
})
@AllArgsConstructor
@NoArgsConstructor
public class FotoP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_codigo", referencedColumnName = "codigo", nullable = false)
    private Producto producto;

    @Column(nullable = false, length = 1000)
    private String url;

    private String nombreArchivo;

}
