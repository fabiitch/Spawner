package com.github.fabiitch.spawner;

import com.github.fabiitch.spawner.archetype.Archetype;
import com.github.fabiitch.spawner.archetype.ArchetypeBuilder;
import com.github.fabiitch.spawner.archetype.criteria.QueryCriteria;
import com.github.fabiitch.spawner.behavior.BehaviorMapper;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.data.behaviors.AttackBehavior;
import com.github.fabiitch.spawner.data.behaviors.DefenseBehavior;
import com.github.fabiitch.spawner.data.components.PositionComponent;
import com.github.fabiitch.spawner.data.components.VelocityComponent;
import com.github.fabiitch.spawner.data.components.attack.KnifeComponent;
import com.github.fabiitch.spawner.data.components.attack.PoisonAuraComponent;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.data.components.defense.ArmorComponent;
import com.github.fabiitch.spawner.data.components.defense.ShieldComponent;
import com.github.fabiitch.spawner.data.components.move.MoveComponent;
import com.github.fabiitch.spawner.data.flags.DeathFlag;
import com.github.fabiitch.spawner.data.flags.OutFlag;
import com.github.fabiitch.spawner.data.wrappers.Knight;
import com.github.fabiitch.spawner.entity.mapper.EntityMapper;
import com.github.fabiitch.spawner.entity.mapper.fillers.BehaviorFiller;
import com.github.fabiitch.spawner.entity.mapper.fillers.ComponentFiller;
import com.github.fabiitch.spawner.entity.mapper.fillers.FlagFiller;
import com.github.fabiitch.spawner.factory.Factory;
import com.github.fabiitch.spawner.factory.GdxPooledFactory;
import com.github.fabiitch.spawner.flag.FlagMapper;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import com.github.fabiitch.spawner.utils.collections.Tab;
import org.junit.jupiter.api.BeforeEach;


public abstract class BaseTest {

    public World world;
    public WorldConfig config;

    public static BehaviorMapper<AttackBehavior> attackBehaviorMapper;
    public static BehaviorMapper<DefenseBehavior> defenseBehaviorMapper;

    public static FlagMapper deathFlagMapper;
    public static FlagMapper outFlagMapper;

    public static ComponentMapper<SwordComponent> swordMapper;
    public static ComponentMapper<KnifeComponent> knifeMapper;
    public static ComponentMapper<PoisonAuraComponent> poisonAuraMapper;
    public static ComponentMapper<ArmorComponent> armorMapper;
    public static ComponentMapper<ShieldComponent> shieldMapper;
    public static ComponentMapper<MoveComponent> moveMapper;
    public static ComponentMapper<PositionComponent> positionMapper;
    public static ComponentMapper<VelocityComponent> velocityMapper;

    public static Archetype swordArchetype;
    public static Archetype attackArchetype;
    public static Archetype moveAttackArchetype;
    public static Archetype staticArchetype;


    public static EntityMapper<Knight> knightMapper;

    @BeforeEach
    public void initWorld() {
        world = new World();
        config = world.getConfig();

        //register
        config.registerBehavior(AttackBehavior.class);
        config.registerComponents(SwordComponent.class, KnifeComponent.class, PoisonAuraComponent.class);


        config.registerBehavior(DefenseBehavior.class);
        config.registerComponents(ArmorComponent.class, ShieldComponent.class);

        config.registerComponent(MoveComponent.class);

        config.registerComponents(PositionComponent.class, VelocityComponent.class);

        config.registerFlags(DeathFlag.class, OutFlag.class);

        //mappers
        attackBehaviorMapper = world.getBehaviorMapper(AttackBehavior.class);
        defenseBehaviorMapper = world.getBehaviorMapper(DefenseBehavior.class);

        swordMapper = world.getComponentMapper(SwordComponent.class);
        knifeMapper = world.getComponentMapper(KnifeComponent.class);
        poisonAuraMapper = world.getComponentMapper(PoisonAuraComponent.class);

        armorMapper = world.getComponentMapper(ArmorComponent.class);
        shieldMapper = world.getComponentMapper(ShieldComponent.class);

        moveMapper = world.getComponentMapper(MoveComponent.class);
        positionMapper = world.getComponentMapper(PositionComponent.class);
        velocityMapper = world.getComponentMapper(VelocityComponent.class);

        deathFlagMapper = world.getFlagMapper(DeathFlag.class);
        outFlagMapper = world.getFlagMapper(OutFlag.class);

        swordArchetype = config.registerArchetype(ArchetypeBuilder.get()
                .components(QueryCriteria.OneOf, SwordComponent.class));

        attackArchetype = config.registerArchetype(ArchetypeBuilder.get()
                .behaviors(QueryCriteria.OneOf, AttackBehavior.class));

        moveAttackArchetype = config.registerArchetype(ArchetypeBuilder.get()
                .components(QueryCriteria.OneOf, MoveComponent.class));

        staticArchetype = config.registerArchetype(ArchetypeBuilder.get()
                .components(QueryCriteria.NoneOf, MoveComponent.class));

        staticArchetype = config.registerArchetype(ArchetypeBuilder.get()
                .components(QueryCriteria.OneOf, MoveComponent.class)
                .behaviors(QueryCriteria.OneOf, AttackBehavior.class));


        Factory<Knight> knightFactory = new GdxPooledFactory<>(Knight.class);
        knightMapper = new EntityMapper<>(knightFactory);
        ComponentFiller<Knight, PositionComponent> positionFiller = new ComponentFiller<Knight, PositionComponent>(positionMapper) {
            @Override
            public void fill(Knight knight, PositionComponent component) {
                knight.setPosition(component);
            }
        };
        ComponentFiller<Knight, SwordComponent> swordFiller = new ComponentFiller<Knight, SwordComponent>(swordMapper) {
            @Override
            public void fill(Knight knight, SwordComponent component) {
                knight.setSwordComponent(component);
            }
        };

        BehaviorFiller<Knight, AttackBehavior> attackFiller = new BehaviorFiller<Knight, AttackBehavior>(attackBehaviorMapper) {
            @Override
            public void fill(Knight knight, SafeTab<AttackBehavior> behaviorTab) {
                if (behaviorTab == null || behaviorTab.isEmpty())
                    knight.setAttackBehavior(null);
                else
                    knight.setAttackBehavior(behaviorTab.get(behaviorTab.size() - 1));
            }
        };
        FlagFiller<Knight> deathFiller = new FlagFiller<Knight>(deathFlagMapper) {
            @Override
            public void fill(Knight knight, boolean hasFlag) {
                knight.setDead(hasFlag);
            }
        };
        knightMapper.addFiller(positionFiller).addFiller(swordFiller).addFiller(attackFiller).addFiller(deathFiller);
        config.registerEntityMapper(knightMapper);
    }
}
