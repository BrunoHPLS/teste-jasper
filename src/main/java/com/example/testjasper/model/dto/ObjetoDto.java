package com.example.testjasper.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjetoDto{
    private Long id;
    private String titulo;
    private Integer dadoNumerico;
    private LocalDateTime dataCriacao;
}
