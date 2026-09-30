package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.TaxRateComponentRequest;
import com.garmentx.catalog.dto.TaxRateComponentResponse;
import com.garmentx.catalog.entity.TaxRate;
import com.garmentx.catalog.entity.TaxRateComponent;
import com.garmentx.catalog.entity.TaxRateComponentStatus;
import com.garmentx.catalog.exception.DuplicateResourceException;
import com.garmentx.catalog.exception.ResourceNotFoundException;
import com.garmentx.catalog.repository.TaxRateComponentRepository;
import com.garmentx.catalog.repository.TaxRateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaxRateComponentServiceImpl
        implements TaxRateComponentService {

    private final TaxRateComponentRepository componentRepository;
    private final TaxRateRepository taxRateRepository;

    public TaxRateComponentServiceImpl(
            TaxRateComponentRepository componentRepository,
            TaxRateRepository taxRateRepository) {

        this.componentRepository = componentRepository;
        this.taxRateRepository = taxRateRepository;
    }

    @Override
    public TaxRateComponentResponse createComponent(
            TaxRateComponentRequest request) {

        TaxRate taxRate = getTaxRate(request.getTaxRateId());

        if (componentRepository.existsByTaxRateIdAndComponentType(
                taxRate.getId(),
                request.getComponentType())) {

            throw new DuplicateResourceException(
                    "Tax component "
                            + request.getComponentType()
                            + " already exists for tax rate id: "
                            + taxRate.getId()
            );
        }

        TaxRateComponentStatus status =
                request.getStatus() != null
                        ? request.getStatus()
                        : TaxRateComponentStatus.ACTIVE;

        TaxRateComponent component =
                new TaxRateComponent();

        component.setTaxRate(taxRate);
        component.setComponentType(
                request.getComponentType()
        );
        component.setRate(request.getRate());
        component.setStatus(status);

        TaxRateComponent savedComponent =
                componentRepository.save(component);

        return mapToResponse(savedComponent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaxRateComponentResponse> getAllComponents() {

        return componentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TaxRateComponentResponse getComponentById(
            Long id) {

        TaxRateComponent component =
                componentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Tax rate component not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(component);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaxRateComponentResponse>
    getComponentsByTaxRate(Long taxRateId) {

        getTaxRate(taxRateId);

        return componentRepository
                .findByTaxRateId(taxRateId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TaxRateComponentResponse updateComponent(
            Long id,
            TaxRateComponentRequest request) {

        TaxRateComponent component =
                componentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Tax rate component not found with id: "
                                                + id
                                )
                        );

        TaxRate taxRate =
                getTaxRate(request.getTaxRateId());

        if (componentRepository
                .existsByTaxRateIdAndComponentTypeAndIdNot(
                        taxRate.getId(),
                        request.getComponentType(),
                        id)) {

            throw new DuplicateResourceException(
                    "Tax component "
                            + request.getComponentType()
                            + " already exists for tax rate id: "
                            + taxRate.getId()
            );
        }

        TaxRateComponentStatus status =
                request.getStatus() != null
                        ? request.getStatus()
                        : component.getStatus();

        component.setTaxRate(taxRate);

        component.setComponentType(
                request.getComponentType()
        );

        component.setRate(request.getRate());

        component.setStatus(status);

        TaxRateComponent updatedComponent =
                componentRepository.save(component);

        return mapToResponse(updatedComponent);
    }

    @Override
    public void deleteComponent(Long id) {

        TaxRateComponent component =
                componentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Tax rate component not found with id: "
                                                + id
                                )
                        );

        componentRepository.delete(component);
    }

    private TaxRate getTaxRate(Long taxRateId) {

        return taxRateRepository.findById(taxRateId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tax rate not found with id: "
                                        + taxRateId
                        )
                );
    }

    private TaxRateComponentResponse mapToResponse(
            TaxRateComponent component) {

        TaxRate taxRate = component.getTaxRate();

        TaxRateComponentResponse response =
                new TaxRateComponentResponse();

        response.setId(component.getId());

        response.setId(component.getId());

        response.setTaxRateId(
                taxRate.getId()
        );

        response.setTaxCategoryName(
                taxRate.getTaxCategory().getName()
        );

        response.setTaxCategoryCode(
                taxRate.getTaxCategory().getCode()
        );

        response.setTaxRate(
                taxRate.getRate()
        );

        response.setComponentType(
                component.getComponentType()
        );

        response.setRate(
                component.getRate()
        );

        response.setStatus(
                component.getStatus()
        );

        response.setCreatedAt(
                component.getCreatedAt()
        );

        response.setUpdatedAt(
                component.getUpdatedAt()
        );

        return response;
    }
}