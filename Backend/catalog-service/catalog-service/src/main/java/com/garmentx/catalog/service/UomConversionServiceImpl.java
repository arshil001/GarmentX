package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.UomConversionRequest;
import com.garmentx.catalog.dto.UomConversionResponse;
import com.garmentx.catalog.entity.Uom;
import com.garmentx.catalog.entity.UomConversion;
import com.garmentx.catalog.entity.UomConversionStatus;
import com.garmentx.catalog.exception.DuplicateResourceException;
import com.garmentx.catalog.exception.ResourceNotFoundException;
import com.garmentx.catalog.repository.UomConversionRepository;
import com.garmentx.catalog.repository.UomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UomConversionServiceImpl
        implements UomConversionService {

    private final UomConversionRepository conversionRepository;
    private final UomRepository uomRepository;

    public UomConversionServiceImpl(
            UomConversionRepository conversionRepository,
            UomRepository uomRepository
    ) {
        this.conversionRepository = conversionRepository;
        this.uomRepository = uomRepository;
    }

    @Override
    public UomConversionResponse createConversion(
            UomConversionRequest request
    ) {

        if (request.getFromUomId()
                .equals(request.getToUomId())) {

            throw new IllegalArgumentException(
                    "From UOM and To UOM cannot be the same"
            );
        }

        if (conversionRepository
                .existsByFromUomIdAndToUomId(
                        request.getFromUomId(),
                        request.getToUomId()
                )) {

            throw new DuplicateResourceException(
                    "Conversion between the selected UOMs already exists"
            );
        }

        Uom fromUom = uomRepository.findById(
                        request.getFromUomId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "From UOM with id "
                                        + request.getFromUomId()
                                        + " not found"
                        )
                );

        Uom toUom = uomRepository.findById(
                        request.getToUomId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "To UOM with id "
                                        + request.getToUomId()
                                        + " not found"
                        )
                );

        UomConversion conversion = new UomConversion();

        conversion.setFromUom(fromUom);
        conversion.setToUom(toUom);
        conversion.setConversionFactor(
                request.getConversionFactor()
        );

        conversion.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : UomConversionStatus.ACTIVE
        );

        UomConversion saved =
                conversionRepository.save(conversion);

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UomConversionResponse getConversionById(Long id) {

        UomConversion conversion =
                conversionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "UOM conversion with id "
                                                + id
                                                + " not found"
                                )
                        );

        return mapToResponse(conversion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UomConversionResponse> getAllConversions() {

        return conversionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UomConversionResponse updateConversion(
            Long id,
            UomConversionRequest request
    ) {

        UomConversion conversion =
                conversionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "UOM conversion with id "
                                                + id
                                                + " not found"
                                )
                        );

        if (request.getFromUomId()
                .equals(request.getToUomId())) {

            throw new IllegalArgumentException(
                    "From UOM and To UOM cannot be the same"
            );
        }

        if (conversionRepository
                .existsByFromUomIdAndToUomIdAndIdNot(
                        request.getFromUomId(),
                        request.getToUomId(),
                        id
                )) {

            throw new DuplicateResourceException(
                    "Conversion between the selected UOMs already exists"
            );
        }

        Uom fromUom = uomRepository.findById(
                        request.getFromUomId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "From UOM with id "
                                        + request.getFromUomId()
                                        + " not found"
                        )
                );

        Uom toUom = uomRepository.findById(
                        request.getToUomId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "To UOM with id "
                                        + request.getToUomId()
                                        + " not found"
                        )
                );

        conversion.setFromUom(fromUom);
        conversion.setToUom(toUom);
        conversion.setConversionFactor(
                request.getConversionFactor()
        );

        if (request.getStatus() != null) {
            conversion.setStatus(request.getStatus());
        }

        UomConversion updated =
                conversionRepository.save(conversion);

        return mapToResponse(updated);
    }

    @Override
    public void deleteConversion(Long id) {

        UomConversion conversion =
                conversionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "UOM conversion with id "
                                                + id
                                                + " not found"
                                )
                        );

        conversionRepository.delete(conversion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UomConversionResponse> getConversionsFromUom(
            Long fromUomId
    ) {

        if (!uomRepository.existsById(fromUomId)) {
            throw new ResourceNotFoundException(
                    "UOM with id " + fromUomId + " not found"
            );
        }

        return conversionRepository
                .findByFromUomId(fromUomId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UomConversionResponse> getConversionsToUom(
            Long toUomId
    ) {

        if (!uomRepository.existsById(toUomId)) {
            throw new ResourceNotFoundException(
                    "UOM with id " + toUomId + " not found"
            );
        }

        return conversionRepository
                .findByToUomId(toUomId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private UomConversionResponse mapToResponse(
            UomConversion conversion
    ) {

        UomConversionResponse response =
                new UomConversionResponse();

        response.setId(conversion.getId());

        Uom fromUom = conversion.getFromUom();
        Uom toUom = conversion.getToUom();

        response.setFromUomId(fromUom.getId());
        response.setFromUomName(fromUom.getName());
        response.setFromUomCode(fromUom.getCode());

        response.setToUomId(toUom.getId());
        response.setToUomName(toUom.getName());
        response.setToUomCode(toUom.getCode());

        response.setConversionFactor(
                conversion.getConversionFactor()
        );

        response.setStatus(
                conversion.getStatus()
        );

        response.setCreatedAt(
                conversion.getCreatedAt()
        );

        response.setUpdatedAt(
                conversion.getUpdatedAt()
        );

        return response;
    }
}