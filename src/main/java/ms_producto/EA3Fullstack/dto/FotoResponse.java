package ms_producto.EA3Fullstack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FotoResponse {

    private Long id;
    private String codigoAsociado;
    private String url;
    private String nombreArchivo;

}
