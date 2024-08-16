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
import com.github.fabiitch.spawner.data.signals.AiPathFinderBehavior;
import com.github.fabiitch.spawner.data.signals.FollowPathFinder;
import com.github.fabiitch.spawner.data.signals.SimplePathFinder;
import com.github.fabiitch.spawner.data.signals.StateMachineSignal;
import com.github.fabiitch.spawner.data.wrappers.Character;
import com.github.fabiitch.spawner.data.wrappers.Knight;
import com.github.fabiitch.spawner.factory.Factory;
import com.github.fabiitch.spawner.factory.GdxPooledFactory;
import com.github.fabiitch.spawner.flag.FlagMapper;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import com.github.fabiitch.spawner.wrapper.EntityMapper;
import com.github.fabiitch.spawner.wrapper.fillers.BehaviorFiller;
import com.github.fabiitch.spawner.wrapper.fillers.ComponentFiller;
import com.github.fabiitch.spawner.wrapper.fillers.FlagFiller;
import org.junit.jupiter.api.BeforeEach;


public abstract class BaseTest {

    public World world;
    public WorldConfig config;

    public static BehaviorMapper<AiPathFinderBehavior> pathFinderMapper;
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

    public static ComponentMapper<FollowPathFinder> followPathFinderMapper;
    public static ComponentMapper<SimplePathFinder> simplePathFinderMapper;
    public static ComponentMapper<StateMachineSignal> stateMachineSignalMapper;

    public static Archetype swordArchetype;
    public static Archetype attackArchetype;
    public static Archetype moveAttackArchetype;
    public static Archetype staticArchetype;


    public static EntityMapper<Knight> knightMapper;
    public static EntityMapper<Character> characterMapper;

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

        config.registerBehavior(AiPathFinderBehavior.class);
        config.registerComponents(SimplePathFinder.class, FollowPathFinder.class);
        config.registerComponents(StateMachineSignal.class);

        //mappers
        attackBehaviorMapper = world.getBehaviorMapper(AttackBehavior.class);
        defenseBehaviorMapper = world.getBehaviorMapper(DefenseBehavior.class);
        pathFinderMapper = world.getBehaviorMapper(AiPathFinderBehavior.class);

        swordMapper = world.getComponentMapper(SwordComponent.class);
        knifeMapper = world.getComponentMapper(KnifeComponent.class);
        poisonAuraMapper = world.getComponentMapper(PoisonAuraComponent.class);

        armorMapper = world.getComponentMapper(ArmorComponent.class);
        shieldMapper = world.getComponentMapper(ShieldComponent.class);

        moveMapper = world.getComponentMapper(MoveComponent.class);
        positionMapper = world.getComponentMapper(PositionComponent.class);
        velocityMapper = world.getComponentMapper(VelocityComponent.class);

        simplePathFinderMapper = world.getComponentMapper(SimplePathFinder.class);
        followPathFinderMapper = world.getComponentMapper(FollowPathFinder.class);
        stateMachineSignalMapper = world.getComponentMapper(StateMachineSignal.class);

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
        Factory<Character> characterFactory = new GdxPooledFactory<>(Character.class);

        knightMapper = new EntityMapper<>(knightFactory);
        characterMapper = new EntityMapper<>(characterFactory);

        ComponentFiller<Character, PositionComponent> positionFiller = new ComponentFiller<Character, PositionComponent>(positionMapper) {
            @Override
            public void fill(Character character, PositionComponent position) {
                character.setPosition(position);
            }
        };
        ComponentFiller<Character, VelocityComponent> velocityFiller = new ComponentFiller<Character, VelocityComponent>(velocityMapper) {
            @Override
            public void fill(Character character, VelocityComponent velocity) {
                character.setVelocity(velocity);
            }
        };
        FlagFiller<Character> deathFiller = new FlagFiller<Character>(deathFlagMapper) {
            @Override
            public void fill(Character character, boolean hasFlag) {
                character.setDead(hasFlag);
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

        characterMapper.addFiller(positionFiller).addFiller(velocityFiller).addFiller(deathFiller);

        knightMapper.useFillerOf(characterMapper);

        knightMapper.addFiller(swordFiller).addFiller(attackFiller);
        config.registerEntityMapper(knightMapper);
    }
}
