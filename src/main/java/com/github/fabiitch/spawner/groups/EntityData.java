package com.github.fabiitch.spawner.groups;

import com.badlogic.gdx.utils.Pool;
import com.github.fabiitch.spawner.wrapper.EntityWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EntityData<D> implements EntityWrapper, Pool.Poolable {

    private int entityId;
    private D data;

    @Override
    public void reset() {
        this.entityId = 0;
        this.data = null;
    }
}
