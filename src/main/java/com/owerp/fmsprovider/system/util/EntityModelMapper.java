package com.owerp.fmsprovider.system.util;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class EntityModelMapper {

    private static final Logger LOG = LoggerFactory.getLogger(EntityModelMapper.class);

    private ApplicationContext applicationContext;

    public EntityModelMapper(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public <T> T getEntity(Object dto, Class<T> entityClz) {
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
                if (entityField.isPresent() && !isGenericCollection(field)) {

                    // fetch getter and setter
                    String getterName = field.getType().equals(boolean.class) ? "is" + StringUtils.capitalize(field.getName()) :
                            "get" + StringUtils.capitalize(field.getName());

                    // if getters are generated using lombok then it's name is prefixed by 'get' not 'is'
//                    String getterName = "get" + StringUtils.capitalize(field.getName());
                    String setterName = "set" + StringUtils.capitalize(field.getName());
                    Method getter = dto.getClass().getDeclaredMethod(getterName);
                    Method setter = entityClz.getDeclaredMethod(setterName, entityField.get().getType());

                    // setting values in new object
                    Object value = getter.invoke(dto);
                    if (isRelatedField(entityField.get(), field, value)) {
                        value = this.getRelatedEntity(field.getType(), entityField.get().getType(), value);
                    }
                    setter.invoke(entity, value);
                }
            } catch (Exception e) {
                LOG.error("[ErrorCreatingEntity] " + e);
            }
        }

        return entity;
    }

    public <T> T getDTO(Object entity, Class<T> dtoClz) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, dtoClz);
    }

    private boolean isRelatedField(Field entityField, Field dtoField, Object value) {
        boolean isRelated = entityField.getAnnotation(OneToOne.class) != null || entityField.getAnnotation(ManyToOne.class) != null || entityField.getAnnotation(OneToMany.class) != null;
        if (!isRelated || value == null) {
            return false;
        }

        try {
            Class<?> dtoClass = dtoField.getType();
            Method method = dtoClass.getDeclaredMethod("getId");
            Object id = method.invoke(value);
            if (id == null || ((long)id == 0)) {
                value = null;
                return false;
            } else {
                return true;
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return false;
        }
    }

    private Object getRelatedEntity(Class<?> dtoClass, Class<?> entityClass, Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Method idGetter = dtoClass.getDeclaredMethod("getId");
        final Long id = (Long) idGetter.invoke(object);
        String repoName = entityClass.getSimpleName() + "Repository";
        JpaRepository<Object, Long> repository = (JpaRepository<Object, Long>) this.applicationContext.getBean(StringUtils.uncapitalize(repoName));
        return repository.getById(id);
    }

    public boolean isGenericCollection(Field field){
        return (field.getType().getSimpleName().equals("List"));
    }

}
