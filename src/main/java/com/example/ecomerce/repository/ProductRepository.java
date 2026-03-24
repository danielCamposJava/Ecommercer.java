package com.example.ecomerce.repository;

import com.example.ecomerce.entity.ProductEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class ProductRepository implements JpaRepository<ProductEntity, UUID> {
    @Override
    public void flush() {

    }

    @Override
    public <S extends ProductEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ProductEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<ProductEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ProductEntity getOne(UUID uuid) {
        return null;
    }

    @Override
    public ProductEntity getById(UUID uuid) {
        return null;
    }

    @Override
    public ProductEntity getReferenceById(UUID uuid) {
        return null;
    }

    @Override
    public <S extends ProductEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ProductEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends ProductEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends ProductEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ProductEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ProductEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ProductEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends ProductEntity> S save(S entity) {
        return null;
    }

    @Override
    public <S extends ProductEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<ProductEntity> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public List<ProductEntity> findAll() {
        return List.of();
    }

    @Override
    public List<ProductEntity> findAllById(Iterable<UUID> uuids) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(ProductEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends ProductEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ProductEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<ProductEntity> findAll(Pageable pageable) {
        return null;
    }
}
