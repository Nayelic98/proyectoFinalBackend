package ec.edu.ups.icc.proyectofinal.advice.services;

import java.util.List;

import ec.edu.ups.icc.proyectofinal.advice.dtos.AdviceResponseDto;
import ec.edu.ups.icc.proyectofinal.advice.dtos.CreateAdviceDto;
import ec.edu.ups.icc.proyectofinal.advice.dtos.UpdateAdviceDto;
public interface AdviceService {
    List<AdviceResponseDto> findByProgramadorId(Long programadorId);
        List<AdviceResponseDto> findByUsuarioId(Long usuarioId);

    AdviceResponseDto create(CreateAdviceDto dto);
    AdviceResponseDto update(Long id, UpdateAdviceDto dto);
    List<AdviceResponseDto> findAll();
}
