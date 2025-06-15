package com.govideo.gerenciador.controllers;

import com.govideo.gerenciador.dtos.EquipmentDTO;
import com.govideo.gerenciador.dtos.MessageResponseDTO;
import com.govideo.gerenciador.entities.enuns.StatusEquipment;
import com.govideo.gerenciador.forms.EquipmentForm;
import com.govideo.gerenciador.services.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Tag(name = "Equipamentos Endpoint")
@RestController
@RequestMapping("/equipamentos")
@SecurityRequirement(name = "bearer-key")
public class EquipamentoController {

    @Autowired
    EquipmentService equipmentService;

    @GetMapping
    @Operation(summary = "Listar todos os equipamentos")
    public ResponseEntity<Page<EquipmentDTO>> consultar(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable paginacao) {
        Page<EquipmentDTO> equipamentosDtos = equipmentService.consultar(paginacao);
        return ResponseEntity.ok().body(equipamentosDtos);
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar equipamentos ativos")
    public ResponseEntity<Page<EquipmentDTO>> consultarAtivos(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable paginacao) {
        Page<EquipmentDTO> equipamentosDtos = equipmentService.consultarAtivos(paginacao);
        return ResponseEntity.ok().body(equipamentosDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar equipamento por ID")
    public ResponseEntity<EquipmentDTO> consultarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(equipmentService.consultarPorIdRetornarDTO(id));
    }

    @GetMapping("/searchByStatus/{status}")
    @Operation(summary = "Listar equipamentos por status")
    public ResponseEntity<Page<EquipmentDTO>> consultarPorStatus(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable paginacao,
            @PathVariable("status") StatusEquipment status) {
        return ResponseEntity.ok().body(equipmentService.findAllByStatus(status, paginacao));
    }

    @PostMapping
    @Operation(summary = "Cadastrar equipamento")
    public ResponseEntity<EquipmentDTO> cadastrar(@Valid @RequestBody EquipmentForm equipmentForm, UriComponentsBuilder uriBuilder) {
        EquipmentDTO equipmentDTO = equipmentService.cadastrar(equipmentForm);
        URI uri = uriBuilder.path("/equipamentos/{id}").buildAndExpand(equipmentDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(equipmentDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar equipamento")
    public ResponseEntity<EquipmentDTO> alterar(@PathVariable Long id, @Valid @RequestBody EquipmentForm equipmentForm) {
        return ResponseEntity.ok().body(equipmentService.alterar(id, equipmentForm));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete or inactivate equipaments")
    public ResponseEntity<MessageResponseDTO> excluir(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(equipmentService.deleteOrInactivateEquipment(id));
    }

}
