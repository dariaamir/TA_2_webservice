Feature: Todos API call returns list of all todos

  Background:

  Scenario Outline: Get all todos for user
    When user requests for all todos by user id <id>
    Then response code is 200
    And response returns correct number of todos <number>

    Examples:
      |id |number |
      |1  |20     |
      |5  |20     |
      |10 |20     |

  Scenario Outline: Get number of completed todos
    When user requests for all todos by user id <id>
    Then response code is 200
    And response returns correct number of completed todos <number>

    Examples:
      |id |number|
      |2  |8     |
      |3  |7     |
      |9  |8     |

  Scenario Outline: Get todo status
  When user requests for todo by id <id>
  Then response code is 200
  And response returns correct status <status>

    Examples:
      |id  |status |
      |4   |true   |
      |56  |true   |
      |100 |false  |