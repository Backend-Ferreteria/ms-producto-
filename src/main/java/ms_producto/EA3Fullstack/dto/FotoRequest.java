package ms_producto.EA3Fullstack.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FotoRequest {

    @NotBlank(message = "El código asociado es obligatorio")
    private String codigoAsociado;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    private String url;

    private String nombreArchivo;
}