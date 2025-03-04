package br.com.fiap.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.ecommerce.dtos.ItemPedidoRequestCreateDto;
import br.com.fiap.ecommerce.dtos.ItemPedidoRequestUpdateDto;
import br.com.fiap.ecommerce.dtos.ItemPedidoResponseDto;
import br.com.fiap.ecommerce.mapper.ItemPedidoMapper;
import br.com.fiap.ecommerce.service.ItemPedidoService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("itempedido")
@RequiredArgsConstructor
public class ItemPedidoController {
    
    private final ItemPedidoService itemPedidoService;
    private final ItemPedidoMapper itemPedidoMapper;

    @GetMapping
    public ResponseEntity<List<ItemPedidoResponseDto>> list() {
        List<ItemPedidoResponseDto> dtos = itemPedidoService.list()
                                                .stream()
                                                .map(e -> itemPedidoMapper.toDto(e))
                                                .toList();
        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping
    public ResponseEntity<ItemPedidoResponseDto> create(@RequestBody ItemPedidoRequestCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                    itemPedidoMapper.toDto(
                        itemPedidoService.save(itemPedidoMapper.toModel(dto))
                    )
                );
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        if (!itemPedidoService.existsById(id)) {
            throw new RuntimeException("id inexistente");
        }

        itemPedidoService.delete(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<ItemPedidoResponseDto> update(
        @PathVariable Long id,
        @RequestBody ItemPedidoRequestUpdateDto dto
    ) {
        if (!itemPedidoService.existsById(id)) {
            throw new RuntimeException("Id inexiste");
        }
        return ResponseEntity
                .ok()
                .body(
                    itemPedidoMapper.toDto(
                        itemPedidoService.save(itemPedidoMapper.toModel(id, dto))
                    )
                );
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemPedidoResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(
                    itemPedidoService
                        .findById(id)
                        .map(e -> itemPedidoMapper.toDto(e))
                        .orElseThrow(() -> new RuntimeException("Id inexistente"))
                );   
    }
}
