package apptive.pieceOfCake.base.impl;

import apptive.pieceOfCake.base.BaseEntity;
import apptive.pieceOfCake.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BaseServiceImpl<E extends BaseEntity, Rs, R extends JpaRepository<E, Long>> implements BaseService<E, Rs, R> {

    private final R repository;

    @Autowired
    ModelMapper modelMapper;

    public List<Rs> findAll() {

        List<E> eList = repository.findAll();
        List<Rs> RsList = new ArrayList<>();

        for(E e : eList) {
        Rs rs = modelMapper.map(e, getResponseType());
        RsList.add(rs);
        }

        return RsList;
    }

    @Transactional
    public Long delete(Long id) {
        E e = repository.findById(id).get();
        e.setDeleted(true);
        return id;
    }

    // Helper method to get the entity type
    private Class<E> getEntityType() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) parameterizedType.getActualTypeArguments()[0];
    }

    // Helper method to get the response type
    private Class<Rs> getResponseType() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<Rs>) parameterizedType.getActualTypeArguments()[1];
    }
}
