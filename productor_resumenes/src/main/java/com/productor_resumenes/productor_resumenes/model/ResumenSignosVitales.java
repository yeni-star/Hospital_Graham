package com.productor_resumenes.productor_resumenes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumenSignosVitales {

    private Long idPaciente;
    private String nombrePaciente;
    private Integer frecuenciaCardiaca;
    private Double temperatura;
    private String presionArterial;
    private Integer saturacionOxigeno;
    private String estadoGeneral;
    private String fechaResumen;
}