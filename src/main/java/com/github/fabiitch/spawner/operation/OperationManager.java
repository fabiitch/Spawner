package com.github.fabiitch.spawner.operation;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.component.ComponentOperation;
import com.github.fabiitch.spawner.data.DataOperation;

public class OperationManager {

    private Array<EcsOperation> pendings = new Array<>();



    public void doPending(){
        for(EcsOperation ecsOperation : pendings){
            ecsOperation.apply();
        }
        pendings.clear();
    }

    private <C> void onComponent(int entityId, C component, DataOperation dataOperation, ComponentMapper<C> mapper) {
        ComponentOperation operation = new ComponentOperation();
        operation.setEntityId(entityId);
        operation.setComponent(component);
        operation.setMapper(mapper);
        operation.setDataOperation(dataOperation);

    }
}
