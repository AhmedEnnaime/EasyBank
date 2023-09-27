package org.youcode.easybank.dao;

import java.util.Optional;

public interface IData<Entity, Type> {

    public Optional<Entity> create(Entity entity);

    public Optional<Entity> update(Type type, Entity entity);

    public Optional<Entity> findByID(Type type);
}
