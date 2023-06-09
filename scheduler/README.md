# Scheduler Service

This is a simple service that allows you to create events and vote for the date that best suits you.
`Scheduler Service` is part of `Schedutn` application, and it's responsible for managing schedules' workflow.

## Development Environment

### Install Java and Kotlin

Install a `Java 17 SDK` from IntelliJ.

### Install Maven

This package uses Maven for dependency management.

Download Maven for the [official website](https://maven.apache.org/download.cgi).

### Running Local

#### Step 1: Create module from existing source

1. From the File menu, choose `File->New->Module from existing sources`
2. In the resulting dialog, select `grupo-3-tacs/pom.xml`
3. Click Ok

Note that Intellij may take some time rebuilding package indexes.
When you open any file, if prompted to _"Trust"_ the package, then do so.

#### Step 2: Rebuild all packages

1. In the project explorer, right-click `grupo-3-tacs [schedutn]`
2. Choose `Rebuild Module 'schedutn'`

#### Step 3: Launch the REST service

1. In the project explorer, choose `scheduler`
2. Drill down through the folders until you find `SchedulerApplication`
3. Right-click `SchedulerApplication` and choose `Run 'SchedulerApplication'`
4. Verify that the service is running by opening a browser and navigating
   to http://localhost:8080/scheduler-docs
5. If you see the Swagger UI, then the service is running.

## Design

### Domain

The domain is the core of the application. It contains the business logic and the entities that are
used to model the
application.

![Domain](../docs/assets/domain.png)

We identified our main entities as `Event` which represents a scheduling and `Option` the possible
dates for the event.
We have a model for represent these entities, but what we actually need to do is schedule events,
enable/disable voting
and vote for options.

> Sometimes it just isn't a thing.
>
> -- <cite>Eric Evans</cite> in **Domain-Driven Design**

Evans discusses the idea of **Domain Service** operations that don’t have a natural home in an
entity or value object.

### Repository Pattern

We’ll use the Repository pattern, a simplifying abstraction over data storage, allowing us to
decouple our model
layer from the data layer. This simplifying abstraction makes our system more
testable by hiding the complexities of the database.

![Repository Pattern](../docs/assets/repository-pattern.png)

### Spring Boot and Service Layer

There are differences between orchestration logic, business logic, and interfacing code, and we will
use the Service
Layer pattern to take care of orchestrating our workflows and defining the use cases of our system.

![Service Layer](../docs/assets/service-layer.svg)

The service layer will become the main way into our app shows what we’re aiming for: we’re going to
add a Spring Boot
API that will talk to the service layer, which will serve as the entrypoint to our domain model.

### Aggregates and Consistency Boundaries

Revisiting our domain model to talk about invariants and constraints, and see how our domain objects
can maintain their own internal consistency, both conceptually and in persistent storage.

Adding the Schedule aggregate, shows a preview of where we're headed. We will introduce a new model
object called `Schedule`, which will wrap our event, and multiple options. And our _Domain Services_
will be available as Schedule methods instead.

![Aggregates and Consistency Boundaries](../docs/assets/schedule-aggregate.svg)

As we don't want to hold a lock over the entire `Events` collection neither the `Option`, but just
for one particular.
We will use a `version` attribute on our `Schedule` model. This will act as a marker for the whole
state change being complete and to use it as the single resource that concurrent workers can fight
over. This is a optimistic concurrency control.
