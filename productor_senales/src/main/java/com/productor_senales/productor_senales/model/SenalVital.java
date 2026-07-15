package com.productor_senales.productor_senales.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SenalVital {
    
    // Identificador del paciente para saber de quién es la lectura
    private String idPaciente;
    
    private int frecuenciaCardiaca;
    private int presionArterialSistolica;
    private int presionArterialDiastolica;
    private double temperatura;
    private String timestamp;

}