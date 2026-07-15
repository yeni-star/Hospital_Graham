package com.procesador_senales.procesador_senales.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SenalVital {

    private String idPaciente;
    
    private int frecuenciaCardiaca;
    private int presionArterialSistolica;
    private int presionArterialDiastolica;
    private double temperatura;
    private String timestamp;
    
}