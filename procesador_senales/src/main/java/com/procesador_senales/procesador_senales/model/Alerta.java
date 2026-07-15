package com.procesador_senales.procesador_senales.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alerta {
    
    private String idPaciente;
    
    private String nombrePaciente;

    private String tipoAnomalia;

    private String valorDetectado;

    private String mensaje;
    
    private String fechaAlerta;
}