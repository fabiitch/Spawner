package com.github.fabiitch.spawner.query;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QueryTest extends BaseTest {

    @Test
    public void simpleCondition() {

        int entityA = world.createEntity();
        int entityB = world.createEntity();
        int entityC = world.createEntity();
        int entityD = world.createEntity();

        swordMapper.addComponent(entityA, new SwordComponent(10));
        swordMapper.addComponent(entityB, new SwordComponent(30));
        swordMapper.addComponent(entityC, new SwordComponent(10));
        swordMapper.addComponent(entityD, new SwordComponent(25));

        ComponentFilter<SwordComponent> swordMatcher = new ComponentFilter<SwordComponent>(swordMapper) {
            @Override
            public boolean accept(int entityId, SwordComponent component) {
                return component.attack() > 20;
            }
        };

        IntArray entities = world.getEntities(swordMatcher, new IntArray());
        Assertions.assertFalse(entities.contains(entityA));
        Assertions.assertTrue(entities.contains(entityB));
        Assertions.assertFalse(entities.contains(entityC));
        Assertions.assertTrue(entities.contains(entityD));

        swordMapper.getComponent(entityA).setDamage(200);

        entities = world.getEntities(swordMatcher, new IntArray());
        Assertions.assertTrue(entities.contains(entityA));
    }

}
