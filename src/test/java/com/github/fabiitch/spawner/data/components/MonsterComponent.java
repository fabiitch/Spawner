package com.github.fabiitch.spawner.data.components;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonsterComponent {
    public enum MonsterType {Zombie, Demon}

    private MonsterType type;
}
