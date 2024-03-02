Feature: Actor

  Scenario: An actor is created
    Given all valid Actor fields are in the request body
    When a POST request is sent to /actors
    Then an ActorDetailsOutput is returned
    And the actor details are valid
    And the status code is 204 Created

  Scenario: An actor is fetched by ID
    Given an actor exists with ID 42
    When a GET request is made to /actors/42
    Then an ActorDetailsOutput is returned

  Scenario: Actors are listed
    Given 100 actors exist
    And no query params are passed
    When a GET request is made to /actors
    Then a page of actors is returned
    And the page number is 1
    And there are 50 actors in the page
    And there are 2 pages in total

  Scenario: Actors are listed (page 2)
    Given 100 actors exist
    And the 'page' query param is 2
    When a GET request is made to /actors
    Then a page of actors is returned
    And the page number is 2
    And there are 50 actors in the page
    And there are 2 pages in total

  Scenario: Actors are listed (not enough for page)
    Given 25 actors exist
    And no query params are passed
    When a GET request is made to /actors
    Then a page of actors is returned
    And the page number is 1
    And there are 25 actors in the page
    And there is 1 page in total

  Scenario: Actors are listed (limiting page size)
    Given 100 actors exist
    And the 'size' query param is 25
    When a GET request is made to /actors
    Then a page of actors is returned
    And the page number is 1
    And there are 25 actors in the page
    And there are 4 pages in total