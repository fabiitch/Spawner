package com.github.fabiitch.spawner.groups;

import com.github.fabiitch.spawner.utils.collections.SafeIntArray;
import com.github.fabiitch.spawner.utils.collections.SafeTab;

public interface Groups<T> {

     SafeIntArray getEntities();

     SafeTab<T>getAll();


}
