package com.github.fabiitch.spawner.query;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.World;
import com.github.fabiitch.spawner.archetype.criteria.EntityMatcher;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WorldQuery {
    private World world;

    public IntArray getEntities(EntityMatcher entityMatcher) {
        SafeIntArray worldEntities = world.getEntities();

        IntArray result = worldEntities.cpy();
        for (int i = 0; i < worldEntities.size(); i++) {
            int entityIds = worldEntities.get(i);
            if (!entityMatcher.accept(entityIds)) {
                result.removeIndex(i);
            }
        }
        return result;
    }
}
