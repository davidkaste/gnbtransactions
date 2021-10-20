# GNB Transactions
This app is part of a technical interview, but it can be used as a seed project.

## 1. Technical information

### 1.1. Tech Stack
|Feature|Library|Version|
|---|---|---|
| Dependency Injection (IoC) | Koin | 3.0.1 |
| HTTP Requests | Retrofit | 2.9.0 |
| Async/Reactivity | Coroutines + Flow | 1.5.0 |
| Unit Tests | JUnit | 4.13.2 |
| Mocking library | MockK | 1.11.10 |

### 1.2. Architecture
The source code is divided into 3 blocks, _data_, _domain_ and _features_. The first one contains code that has access to the sources of data. _domain_ defines business logic, entities and use cases. And the last of them, _presentation_, has the UI layer with activities/fragments, viewmodels, etc.

This project has been developed following the guidelines of clean code and clean architecture, from Robert C. Martin.

#### 1.2.1. _data_ package
It contains the datasources, repository implementations and API models. Datasources are in charge of requesting the data to API services, local databases or any other source of data. Repositories consume data from the datasources and transform it into domain entities.

#### 1.2.2. _domain_ package
It contains the use cases, domain entities and repositories specs. Use cases are a single method classes that access to one or more repositories to get the data to the presentation layer. Repositories are specified in this layer to avoid having dependencies to the data layer.

#### 1.2.3. _presentation_ package
It contains the _viewmodels_ and _views_ (_activities_, _fragments_...). The _ViewModel_ exposes an observable model and methods that the _view: will subscribe or call to interact with the business logic.

## 2. Future work
- [ ] Use Navigation Component
- [ ] Migrate from Koin to Dagger Hilt (In fact, Koin is not a DI library, but a service locator)
- [ ] UI + Instrumented Tests

## Feedback
#### Pros
- [ ] TBD

#### Cons
- [ ] TBD
