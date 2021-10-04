package com.owerp.fmsprovider.system.util;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class EntityModelMapper {

    private static final Logger LOG = LoggerFactory.getLogger(EntityModelMapper.class);

    public <T> T getEntity(Object dto, Class<T> entityClz){
        T entity;
        try {
            entity = entityClz.getConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            LOG.error("[ErrorCreatingEntity] " + e);
            return null;
        }

        Class<?> dtoClazz = dto.getClass();
        for (Field field : dtoClazz.getDeclaredFields()) {
            try {
                Optional<Field> entityField = Arrays.stream(entityClz.getDeclaredFields()).filter(f -> f.getName().equals(field.getName())).findAny();
                if (entityField.isPresent()) {

                    // fetch getter and setter
                    String getterName = field.getType().equals(Boolean.class) || field.getType().equals(boolean.class) ? "is" + StringUtils.capitalize(field.getName()) :
                            "get" + StringUtils.capitalize(field.getName());
                    String setterName = "set" + StringUtils.capitalize(field.getName());
                    Method getter = dto.getClass().getDeclaredMethod(getterName);
                    Method setter = entityClz.getDeclaredMethod(setterName, field.getType());

                    //if field has one-one then related data needs to be fetched
//                    Annotation[] annotations = entityField.get().getAnnotations();

//                    if(entityField.get().getA)

                    // setting values in new object
                    Object value = getter.invoke(dto);
                    setter.invoke(entity, value);
                }
            } catch (Exception e) {
                LOG.error("[ErrorCreatingEntity] " + e);
            }
        }

        return entity;
    }

    public <T> T getDTO(Object entity, Class<T> dtoClz){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, dtoClz);
    }

}
