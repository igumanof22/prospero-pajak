package com.alurkerja.core.controller;


import com.alurkerja.core.exception.AlurKerjaException;
import com.alurkerja.core.service.CrudService;
import com.alurkerja.spec.entity.BaseDto;
import com.alurkerja.spec.spec.SpecService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin
public abstract class CrudController<E, D, S, R> extends BaseController {
    private static final String NOT_FOUND = "Object not found"; //
    protected CrudService<E, D, R> crudService;
    protected S service;
    protected CrudController(S crudService)
    {
        this.crudService = (CrudService<E, D, R>) crudService;
        this.service = crudService;
    }

    @GetMapping("/spec")
    public ResponseEntity<Object> specifications() throws ClassNotFoundException {
        BaseDto<E, D> baseDto = (BaseDto<E, D>) this.crudService.instantiateDto();
        return success(SpecService.specifications(baseDto.getClass()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id){
        Optional<E> optionalE = crudService.get(id);
        if(optionalE.isPresent()){
            return success(optionalE.get());
        }
        else{
            return error(HttpStatus.NOT_FOUND, NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@Valid @RequestBody D dto) throws AlurKerjaException {
        return success(crudService.createFromDto(dto));
    }


    @PostMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody D dto) throws AlurKerjaException {
        Optional<E> optionalE = crudService.get(id);
        if(optionalE.isPresent()){
            E updatedEntity = crudService.update(optionalE.get(), dto);
            return success(updatedEntity);
        }
        else{
            return error(HttpStatus.NOT_FOUND, NOT_FOUND);
        }
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) throws AlurKerjaException {
    	Optional<E> optionalE = crudService.get(id);
        if(optionalE.isPresent()){
            crudService.delete(optionalE.get());
            return success("Object was deleted");
        }
        else{
            return error(HttpStatus.NOT_FOUND, NOT_FOUND);
        }

    }

    @GetMapping("")
    public ResponseEntity<Object> list(@RequestParam(required = false, defaultValue = "0")int page,
                                       @RequestParam(required = false, defaultValue = "100") int limit,
                                       @RequestParam(required = false) String sort,
                                       @RequestParam(required = false, defaultValue = "true") boolean asc,
                                       D dto){
        Pageable pageable = pageFromRequest(page, limit, sort, asc);
        return success(crudService.findAll(dto, pageable));
    }
}
