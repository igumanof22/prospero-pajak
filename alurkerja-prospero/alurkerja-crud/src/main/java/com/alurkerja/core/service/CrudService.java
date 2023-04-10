package com.alurkerja.core.service;

import com.alurkerja.core.entity.CrudEntity;
import com.alurkerja.core.exception.AlurKerjaException;
import com.alurkerja.core.interfaces.AccessableInterface;
import com.alurkerja.core.interfaces.OwnershipInterface;
import com.alurkerja.core.repository.CrudRepository;
import com.alurkerja.core.repository.search.GenericSearchSpecification;
import com.alurkerja.spec.entity.BaseDto;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public abstract class CrudService<E, D, R> {
    protected CrudRepository<E> jpaRepository;

    protected CrudService(R simpleJpaRepository) {
        this.jpaRepository = (CrudRepository<E>) simpleJpaRepository;
    }

    @SneakyThrows
    public E createFromDto(D dto) {
        if (AccessableInterface.class.isAssignableFrom(this.getClass())) {
            AccessableInterface accessable = (AccessableInterface) this;
            if (!accessable.canAdd()) {
                throw new AlurKerjaException(400, "Can't add new data");
            }
        }
        BaseDto<E, D> dtoable = (BaseDto) dto;
        E entity = dtoable.fromDto();
        CrudEntity crudEntity = (CrudEntity) entity;
        crudEntity.setCreatedDate(new Date());
        crudEntity.setCreatedBy(this.getCurrentUser());
        try {
            this.jpaRepository.save(entity);
            return entity;
        }
        catch (Exception ex){
            throw new AlurKerjaException(ex.getMessage());
        }
    }

    public D createFromDtoAsDto(D dto) throws AlurKerjaException {
        E entity = this.createFromDto(dto);
        BaseDto<E, D> baseDto = (BaseDto<E, D>) dto;
        return baseDto.toDto(entity);
    }

    @SneakyThrows
    public E create(E entity) {
        if (AccessableInterface.class.isAssignableFrom(this.getClass())) {
            AccessableInterface accessable = (AccessableInterface) this;
            if (!accessable.canAdd()) {
                throw new AlurKerjaException(400, "Can't add new data");
            }
        }
        CrudEntity crudEntity = (CrudEntity) entity;
        crudEntity.setCreatedDate(new Date());
        crudEntity.setCreatedBy(this.getCurrentUser());
        this.jpaRepository.save(entity);
        return entity;
    }

    @SneakyThrows
    public List<E> findAll() {
        if (AccessableInterface.class.isAssignableFrom(this.getClass())) {
            AccessableInterface accessable = (AccessableInterface) this;
            if (!accessable.canList()) {
                throw new AlurKerjaException(400, "Can't get list data");
            }
        }
        return this.jpaRepository.findAll();
    }

    @SneakyThrows
    public List<E> findAllById(List<Long> ids) {
        if (AccessableInterface.class.isAssignableFrom(this.getClass())) {
            AccessableInterface accessable = (AccessableInterface) this;
            if (!accessable.canList()) {
                throw new AlurKerjaException(400, "Can't get list data");
            }
        }
        return this.jpaRepository.findAllById(ids);
    }

    @SneakyThrows
    public Page<E> findAll(Pageable pageable) {
        if (AccessableInterface.class.isAssignableFrom(this.getClass())) {
            AccessableInterface accessable = (AccessableInterface) this;
            if (!accessable.canList()) {
                throw new AlurKerjaException(400, "Can't get list data");
            }
        }
        return this.jpaRepository.findAll(pageable);
    }

    @SneakyThrows
    public Page<E> findAll(D keyword, Pageable pageable) {
        if (AccessableInterface.class.isAssignableFrom(this.getClass())) {
            AccessableInterface accessable = (AccessableInterface) this;
            if (!accessable.canList()) {
                throw new AlurKerjaException(400, "Can't get list data");
            }
        }
        Specification spec =  this.instantiateSpecification(keyword);
        return this.jpaRepository.findAll(this.createSpec(spec), pageable);
    }

    public Specification createSpec (Specification spec ) {
        if(OwnershipInterface.class.isAssignableFrom(this.getClass())) {
            OwnershipInterface  ownership = (OwnershipInterface) this;
            spec = spec.and(ownership.getMine());
        }
        return spec;
    }

    public Specification createCounterSpec (Specification spec ) {
        if(OwnershipInterface.class.isAssignableFrom(this.getClass())) {
            OwnershipInterface  ownership = (OwnershipInterface) this;
            spec = spec.and(ownership.getMine());
        }
        return spec;
    }

    @SneakyThrows
    public List<D> findAllAsDto() {
        if (AccessableInterface.class.isAssignableFrom(this.getClass())) {
            AccessableInterface accessable = (AccessableInterface) this;
            if (!accessable.canList()) {
                throw new AlurKerjaException(400, "Can't get list data");
            }
        }
        List<E> entities = this.jpaRepository.findAll();
        List<D> dtos = new ArrayList<>();
        for(E entity : entities){
            D dto = this.instantiateDto();
            BaseDto<E, D> baseDto = (BaseDto<E, D>) dto;
            dtos.add(baseDto.toDto(entity));
        }
        return dtos;
    }

    @SneakyThrows
    public Page<D> findAllAsDto(Pageable pageable) {
        if (AccessableInterface.class.isAssignableFrom(this.getClass())) {
            AccessableInterface accessable = (AccessableInterface) this;
            if (!accessable.canList()) {
                throw new AlurKerjaException(400, "Can't get list data");
            }
        }
        Page<E> entities = this.jpaRepository.findAll(pageable);
        List<D> dtos = new ArrayList<>();
        for(E entity : entities){
            D dto = this.instantiateDto();
            BaseDto<E, D> baseDto = (BaseDto<E, D>) dto;
            dtos.add(baseDto.toDto(entity));
        }
        Page<D> page = new PageImpl(dtos, pageable, entities.getTotalElements());
        return page;
    }

    @SneakyThrows
    public Page<D> findAllAsDto(D keyword, Pageable pageable) {
        if (AccessableInterface.class.isAssignableFrom(this.getClass())) {
            AccessableInterface accessable = (AccessableInterface) this;
            if (!accessable.canList()) {
                throw new AlurKerjaException(400, "Can't get list data");
            }
        }
        Specification spec = this.instantiateSpecification(keyword);
        Page<E> entities = this.jpaRepository.findAll(this.createSpec(spec), pageable);
        List<D> dtos = new ArrayList<>();
        for(E entity : entities){
            D dto = this.instantiateDto();
            BaseDto<E, D> baseDto = (BaseDto<E, D>) dto;
            dtos.add(baseDto.toDto(entity));
        }
        Page<D> page = new PageImpl(dtos, pageable, entities.getTotalElements());
        return page;
    }

    @SneakyThrows
    public Optional<E> get(Long id) {
        if (AccessableInterface.class.isAssignableFrom(this.getClass())) {
            AccessableInterface accessable = (AccessableInterface) this;
            if (!accessable.canView()) {
                throw new AlurKerjaException(400, "Can't get detail data");
            }
        }
        return jpaRepository.findById(id);
    }

    @SneakyThrows
    public E update(E entity) {
        if (AccessableInterface.class.isAssignableFrom(this.getClass())) {
            AccessableInterface accessable = (AccessableInterface) this;
            if (!accessable.canEdit()) {
                throw new AlurKerjaException(400, "Can't update data");
            }
        }
        CrudEntity crudEntity = (CrudEntity) entity;
        crudEntity.setUpdatedDate(new Date());
        crudEntity.setUpdatedBy(this.getCurrentUser());
        return this.jpaRepository.save(entity);
    }

    @SneakyThrows
    public E update(E entity, D dto) {
        if (AccessableInterface.class.isAssignableFrom(this.getClass())) {
            AccessableInterface accessable = (AccessableInterface) this;
            if (!accessable.canEdit()) {
                throw new AlurKerjaException(400, "Can't update data");
            }
        }
        BaseDto<E, D> baseDto = (BaseDto<E, D>) dto;
        CrudEntity crudEntity = (CrudEntity) entity;
        baseDto.copyFromDto(entity);
        crudEntity.setUpdatedDate(new Date());
        crudEntity.setUpdatedBy(this.getCurrentUser());
        return this.jpaRepository.save(entity);
    }

    @SneakyThrows
    public void delete(E e) {
        if (AccessableInterface.class.isAssignableFrom(this.getClass())) {
            AccessableInterface accessable = (AccessableInterface) this;
            if (!accessable.canDelete()) {
                throw new AlurKerjaException(400, "Can't delete data");
            }
        }
        this.jpaRepository.delete(e);
    }

    public String getGenericClassName(int index) {
        return ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[index]).getTypeName();
    }

    public String getSimpleClassName(int index) {
        return ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[index]).getSimpleName();
    }

    public D instantiateDto() {
        try {
            Class<?> clazz = Class.forName(((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]).getTypeName());
            Constructor<?> cons = clazz.getConstructor();
            return (D) cons.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Specification<E> instantiateSpecification(D dto) {
        return new GenericSearchSpecification<>(dto);
    }
    public Specification<E> instantiateSpecification(List<String> processInstanceIds, D dto) {
        return new GenericSearchSpecification<>( processInstanceIds, dto);
    }

    public abstract String getCurrentUser();
    public String getCurrentUserName() {
        return null;
    }
    public abstract String getCurrentGroup();
    public abstract String getCurrentOrganization();
}
