package com.github.fabiitch.spawner.query;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.World;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WorldQuery {
    private World world;

    public IntArray getEntities(EntityFilter entityFilter) {
        SafeIntArray worldEntities = world.getEntities();

        IntArray result = worldEntities.cpy();
        for (int i = 0; i < worldEntities.size(); i++) {
            int entityIds = worldEntities.get(i);
            if (!entityFilter.accept(entityIds)) {
                result.removeIndex(i);
            }
        }
        return result;
    }
}
