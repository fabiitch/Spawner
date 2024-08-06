package com.github.fabiitch.spawner.wrapper;

import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.PositionComponent;
import com.github.fabiitch.spawner.data.wrappers.KnightWrapper;
import com.github.fabiitch.spawner.entity.mapper.ComponentFiller;
import com.github.fabiitch.spawner.entity.mapper.EntityMapper;
import org.junit.jupiter.api.Test;

public class EntityWrapperTest extends BaseTest {

    @Test
    public void test() {
        KnightWrapper knightWrapper;

        EntityMapper<KnightWrapper> knightMapper = new EntityMapper<>();

        ComponentFiller<KnightWrapper, PositionComponent> positionFiller = new ComponentFiller<KnightWrapper, PositionComponent>(positionMapper) {
            @Override
            public void fill(KnightWrapper knight) {
                knight.setPosition(getMapper().getComponent(knight.getId()));
            }
        };

    }
}
