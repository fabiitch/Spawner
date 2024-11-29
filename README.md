not finish at all

# Spawnner ECS

## Entity
- Simple int
- addEntity
- removeEntity (dont call your component/behavior/flag listeners)
- destroy remove and call all listeners

## Components
- What u want
- Use ComponentMapper for get

## Behavior
- A classes extended by multiple components
- An entity can have several components of a behavior 
- Behavior listener is always called after component

## Aspect 
- A classes extended by multiple component
- An entity can have only one component of an aspect
- Adding a new component of aspect on an entity who already as aspect remove the previous component 

## Flags
- Similar as component, but only for boolean
- It can be created by class, String or enum (Its register as a String)

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

## Groups
- ComponentGroup => get entities and components who match a ComponentFilter
- BehaviorGroup  => get entities and components who match a BehaviorFilter
- EntityGroup    => get entities who match an EntityFilterTracker
- EntityWrapper  => get entities who match an EntityFilterTracker

## Query 
- Query world using EntityMatcher 
- Archetype is an EntityMatcher, you can use ur own matcher

## World Initialization
1)  config.registerBehavior()
2)  config.registerComponents()
3)  config.registerFamilies()


## Events

### 1) World Events
interface : WorldListener
