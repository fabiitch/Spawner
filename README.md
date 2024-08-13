# Spawnner ECS

## Entity
- Simple int

## Components
- What u want

## Behavior
- Interface or abstract class extends by multiples component

## Flags
- Component attach, but only boolean
- U can create by class, String or enum (It register as String)

## Systems
 - EngineSystem
 - EntitySystem 

## Prototype
- Other way to create an entity with all components already attach

## Family 
- Entities who match an archetype

## Archetype
- Composition of components, behavior, flag 

## EntityMapper - Entity Wrapper
- Encapsulate your entity with your own class
- You must define fillers to describe own fill your object class with components
- see EntityMapperTest

## Query 
- Query world using EntityMatcher 
- Archetype is an EntityMatcher, you can use ur own matcher

## World Initialization
1)  config.registerBehavior()
2)  config.registerComponents
3)  config.registerFamilies()


## Events

### 1) World Events
interface : WorldListener
