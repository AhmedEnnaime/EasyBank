package org.youcode.easybank.dao;

import java.util.List;
import java.util.Optional;

public interface IData<Entity, Identifier> {

    public Optional<Entity> create(Entity entity);

    public Optional<Entity> update(Identifier id, Entity entity);

    public Optional<Entity> findByID(Identifier id);

    public List<Entity> getAll();

    public boolean delete(Identifier id);

    public boolean deleteAll();
}