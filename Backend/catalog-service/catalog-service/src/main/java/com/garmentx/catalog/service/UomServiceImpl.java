package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.UomRequest;
import com.garmentx.catalog.dto.UomResponse;
import com.garmentx.catalog.entity.Uom;
import com.garmentx.catalog.entity.UomStatus;
import com.garmentx.catalog.exception.DuplicateResourceException;
import com.garmentx.catalog.exception.ResourceNotFoundException;
import com.garmentx.catalog.repository.UomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class UomServiceImpl implements UomService {

    private final UomRepository uomRepository;

    public UomServiceImpl(UomRepository uomRepository) {
        this.uomRepository = uomRepository;
    }

    @Override
    public UomResponse createUom(UomRequest request) {

        String name = normalizeName(request.getName());
        String code = normalizeCode(request.getCode());

        if (uomRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException(
                    "UOM with name '" + name + "' already exists"
            );
        }

        if (uomRepository.existsByCodeIgnoreCase(code)) {
            throw new DuplicateResourceException(
                    "UOM with code '" + code + "' already exists"
            );
        }

        Uom uom = new Uom();

        uom.setName(name);
        uom.setCode(code);
        uom.setType(request.getType());
        uom.setConversionFactor(request.getConversionFactor());

        uom.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : UomStatus.ACTIVE
        );

        Uom savedUom = uomRepository.save(uom);

        return mapToResponse(savedUom);
    }

    @Override
    @Transactional(readOnly = true)
    public UomResponse getUomById(Long id) {

        Uom uom = uomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "UOM with id " + id + " not found"
                        )
                );

        return mapToResponse(uom);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UomResponse> getAllUoms() {

        return uomRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UomResponse updateUom(
            Long id,
            UomRequest request
    ) {

        Uom uom = uomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "UOM with id " + id + " not found"
                        )
                );

        String name = normalizeName(request.getName());
        String code = normalizeCode(request.getCode());

        if (uomRepository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new DuplicateResourceException(
                    "UOM with name '" + name + "' already exists"
            );
        }

        if (uomRepository.existsByCodeIgnoreCaseAndIdNot(code, id)) {
            throw new DuplicateResourceException(
                    "UOM with code '" + code + "' already exists"
            );
        }

        uom.setName(name);
        uom.setCode(code);
        uom.setType(request.getType());
        uom.setConversionFactor(request.getConversionFactor());

        if (request.getStatus() != null) {
            uom.setStatus(request.getStatus());
        }

        Uom updatedUom = uomRepository.save(uom);

        return mapToResponse(updatedUom);
    }

    @Override
    public void deleteUom(Long id) {

        Uom uom = uomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "UOM with id " + id + " not found"
                        )
                );

        uomRepository.delete(uom);
    }

    private String normalizeName(String value) {

        return value.trim().replaceAll("\\s+", " ");
    }

    private String normalizeCode(String value) {

        return value.trim().toUpperCase();
    }

    private UomResponse mapToResponse(Uom uom) {

        UomResponse response = new UomResponse();

        response.setId(uom.getId());
        response.setName(uom.getName());
        response.setCode(uom.getCode());
        response.setType(uom.getType());
        response.setConversionFactor(uom.getConversionFactor());
        response.setStatus(uom.getStatus());
        response.setCreatedAt(uom.getCreatedAt());
        response.setUpdatedAt(uom.getUpdatedAt());

        return response;
    }
}