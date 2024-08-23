package com.github.fabiitch.spawner.operation.locker;

import com.github.fabiitch.spawner.DataType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleLock {

    private DataType dataType;
    private int index;
}
