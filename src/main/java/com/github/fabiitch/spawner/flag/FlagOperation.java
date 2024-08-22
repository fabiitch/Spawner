package com.github.fabiitch.spawner.flag;

import com.github.fabiitch.spawner.data.DataChange;
import com.github.fabiitch.spawner.operation.EcsOperation;

public class FlagOperation implements EcsOperation {
    private int entityId;
    private DataChange dataChange;
    private FlagMapper mapper;

    @Override
    public void apply() {

    }
}
