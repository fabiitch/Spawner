package com.github.fabiitch.spawner.groups.components;

import com.badlogic.gdx.utils.Pool;
import com.github.fabiitch.spawner.wrapper.EntityWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EntityData<C> implements EntityWrapper, Pool.Poolable {
    private int entityId;
    private C component;

    public void set(int entityId, C data) {
        this.entityId = entityId;
        this.component = data;
    }

    @Override
    public void reset() {
        this.entityId = -1;
        this.component = null;
    }
}
