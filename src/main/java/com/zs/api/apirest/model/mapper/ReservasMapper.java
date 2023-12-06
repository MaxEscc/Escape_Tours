package com.zs.api.apirest.model.mapper;

import com.zs.api.apirest.model.dto.NuevaReservaDTO;
import com.zs.api.apirest.model.dto.UpdateReservaDTO;
import com.zs.api.apirest.model.entity.ReservasEntity;
import com.zs.api.apirest.services.impl.ReservasImpl;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ReservasMapper {

  @Mapping(target = "reservaId", ignore = true)
  @Mapping(target = "fechaReserva", ignore = true)
  @Mapping(target = "totalPagado", source = "abono")
  ReservasEntity toEntity(NuevaReservaDTO nuevaReservaDTO);

  @Mapping(target = "totalPagado",
           expression =
               "java(entity.getTotalPagado().add(updateReservaDTO.getAbono()))")
  void
  updateFromDto(UpdateReservaDTO updateReservaDTO,
                @MappingTarget ReservasEntity entity);
}
