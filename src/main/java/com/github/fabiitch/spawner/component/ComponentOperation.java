package com.github.fabiitch.spawner.component;

import com.github.fabiitch.spawner.data.DataChange;
import com.github.fabiitch.spawner.operation.EcsOperation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
 public class ComponentOperation implements EcsOperation {
    private int entityId;
    private Object component;
    private ComponentMapper mapper;
    private DataChange dataChange;

    @Override
    public void apply() {
        switch (dataChange){
            case Add:
                mapper.addReally(entityId, component);
                break;
            case Remove:
                break;
            case Update:
                break;
        }
    }
}
