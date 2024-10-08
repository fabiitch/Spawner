package com.github.fabiitch.spawner.component;

import com.github.fabiitch.spawner.data.DataOperation;
import com.github.fabiitch.spawner.operation.EcsOperation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
 public class ComponentOperation implements EcsOperation {
    private int entityId;
    private Object component;
    private ComponentMapper mapper;
    private DataOperation dataOperation;

    @Override
    public void apply() {
        switch (dataOperation){
            case Add:
                mapper.addReallyComponent(entityId, component);
                break;
            case Remove:
                mapper.removeReally(entityId);
                break;
            case Update:
                break;
        }
    }
}
