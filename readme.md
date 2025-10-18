# TaskBoard — (Clean Code Showcase)

A minimal Spring Boot backend for a Kanban-style task board. It demonstrates **7 structural and creational** design patterns with clean, testable code.

> Patterns: **Prototype, Builder, Composite, Factory, Adapter, Bridge, Decorator**

---

## Quick start

**Requirements**
- Java **17+**
- Maven Wrapper
- (Optional) Swagger UI via `springdoc-openapi-starter-webmvc-ui`

**Run**
```bash
./mvnw clean spring-boot:run
# app on http://localhost:8080
```

# useful functional:

Health check: GET /api/ping → pong

H2 console: http://localhost:8080/h2

Swagger: http://localhost:8080/swagger-ui.html

# API documentation:

POST /api/boards → 201
Body: { "name": "Website Redesign", "templateKey": "kanban-basic" }
Location: /api/boards/{id}

GET /api/boards/{id}/tree → board → columns → cards (nested DTOs)

POST /api/boards/{id}/columns → 201
Body: { "name": "QA", "wipLimit": 3 }

POST /api/cards → 201
Body: { "columnId": "<UUID>", "title": "Fix breadcrumbs", ... }
Location: /api/cards/{id}

POST /api/cards/{id}/move → 204
Body: { "toColumnId": "<UUID>" }

POST /api/cards/{id}/assign → 204
Body: { "policy": "ROUND_ROBIN" } or { "assignee": "user:karol" }

# Design Patterns demonstrated
# 1) Prototype (Creational)

What: New boards created by cloning from a template.
Where

board/template/KanbanBasicTemplate.java

board/template/TemplateRegistry.java

board/api/BoardService#createBoard(...)

```PowerShell
Invoke-WebRequest -Method POST "http://localhost:8080/api/boards" `
  -ContentType "application/json" `
  -Body '{"name":"Kanban Demo","templateKey":"kanban-basic"}'
```

# 2) Builder (Creational)

What: Step-by-step, validated construction of a CardEntity.
Where

card/builder/CardBuilder.java

persistence/CardEntity.java → public static CardEntity create(...)

card/service/CardService#create(...)

```PowerShell
$body = @{ columnId = "<COLUMN_ID>"; title = "Implement Builder"; description = "..." } | ConvertTo-Json
Invoke-WebRequest -Method POST "http://localhost:8080/api/cards" -ContentType "application/json" -Body $body
```

# 3) Composite (Structural) — read side

What: Treat Board → Columns → Cards as a uniform tree to serialize.
Where

board/composite/BoardTreeAssembler.java → toDto(...)

web/BoardController#tree(...)

# 3) Composite (Structural) — read side

What: Treat Board → Columns → Cards as a uniform tree to serialize.
Where

board/composite/BoardTreeAssembler.java → toDto(...)

web/BoardController#tree(...)

```PowerShell
Invoke-RestMethod "http://localhost:8080/api/boards/<BOARD_ID>/tree" | ConvertTo-Json -Depth 6
```

# 4) Factory (Method / Simple Factory) (Creational)

What: Select a card assignment strategy by policy.
Where

card/assign/AssignmentPolicy.java

card/assign/AssignmentStrategy.java

card/assign/RoundRobinAssignment.java

card/assign/AssignmentStrategyFactory.java

card/service/CardService#assign(...)

```PowerShell
Invoke-RestMethod -Method POST "http://localhost:8080/api/cards/<CARD_ID>/assign" `
  -ContentType "application/json" -Body '{"policy":"ROUND_ROBIN"}'
```

# 5) Adapter (Structural)

What: Wrap different delivery technologies under a single interface.
Where

Interface: notify/bridge/NotificationChannel.java

Adapters:

notify/adapter/EmailChannel.java (would wrap JavaMail)

notify/adapter/SlackChannel.java (would wrap Slack webhook)

notify/adapter/ConsoleChannel.java (dev stub)

Wiring: config/NotificationConfig.java (selects base channel via taskboard.notifications.channel)

```yaml
# application.yml
taskboard:
  notifications:
    channel: console   # or email | slack (dev stubs print to console)
```

# 6) Bridge (Structural)

What: Decouple what we notify from how we deliver it.
Where

Abstraction: notify/bridge/Notification.java

Implementor: notify/bridge/NotificationChannel.java

Refined abstraction: notify/bridge/CardAssignedNotification.java

Usage: card/service/CardService#assign(...)
new CardAssignedNotification(notificationChannel, card).dispatch(assignee)
Demo: run the Factory demo; it sends a CardAssignedNotification via the configured channel.

# 7) Decorator (Structural)

What: Add logging/retry around notifications without changing channel code.
Where

notify/decorator/LoggingNotificationChannel.java

notify/decorator/RetryingNotificationChannel.java

config/NotificationConfig.java
base → LoggingNotificationChannel → RetryingNotificationChannel(…, 2)

```ini
[CONSOLE] to=user:karol subject=Card assigned: Wire up controller body=...
[NOTIFY][log] to=user:karol took=..ms
```

# PowerShell demo script
```PowerShell
$base = "http://localhost:8080"

# 1) Create board from template
$respBoard = Invoke-WebRequest -Method POST "$base/api/boards" -ContentType "application/json" -Body '{"name":"Kanban Demo","templateKey":"kanban-basic"}'
$boardId = ($respBoard.Headers.Location -split '/')[-1]
"boardId: $boardId"

# 2) Get first column
$tree  = Invoke-RestMethod "$base/api/boards/$boardId/tree"
$colId = $tree.columns[0].id
"columnId: $colId"

# 3) Create a card
$bodyCard = @{ columnId = $colId; title = "Wire up controller"; description = "Create/move/assign endpoints" } | ConvertTo-Json
$respCard = Invoke-WebRequest -Method POST "$base/api/cards" -ContentType "application/json" -Body $bodyCard
$cardId   = ($respCard.Headers.Location -split '/')[-1]
"cardId: $cardId"

# 4) Assign (Factory + Bridge + Adapter + Decorator)
Invoke-RestMethod -Method POST "$base/api/cards/$cardId/assign" -ContentType "application/json" -Body '{"policy":"ROUND_ROBIN"}'
"Assigned card $cardId"
```

# Project Structure 

com.ven.taskboard
├─ board/
│   ├─ api/          # BoardService
│   ├─ composite/    # BoardTreeAssembler  ← Composite
│   └─ template/     # KanbanBasicTemplate, TemplateRegistry  ← Prototype
├─ card/
│   ├─ assign/       # Policy, Strategy, Factory  ← Factory
│   ├─ builder/      # CardBuilder  ← Builder
│   └─ service/      # CardService (create/move/assign)
├─ notify/
│   ├─ bridge/       # Notification, NotificationChannel, CardAssignedNotification  ← Bridge
│   ├─ adapter/      # EmailChannel, SlackChannel, ConsoleChannel  ← Adapter
│   └─ decorator/    # LoggingNotificationChannel, RetryingNotificationChannel  ← Decorator
├─ persistence/      # JPA entities & Spring Data repositories
├─ web/              # Controllers, DTOs
└─ config/           # NotificationConfig wiring decorators
