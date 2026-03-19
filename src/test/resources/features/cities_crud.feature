Feature: Cities CRUD Operations API
  As a backend system integrator
  I want to verify that the cities API works correctly
  So that I can confidently create, read, update, and delete locations

  @api @crud @cities
  Scenario: Full CRUD cycle for a City
    Given the API client is ready to manage cities
    When they create a new city with name "Test City" and country "Test Country"
    Then the city creation response should be successfully generated
    
    When they retrieve the newly created city
    Then the retrieved city details should match "Test City" and "Test Country"
    
    When they update the city name to "Updated City" and country to "Updated Country"
    Then the city update response should be successfully generated
    
    When they retrieve the updated city
    Then the retrieved city details should match "Updated City" and "Updated Country"
    
    When they delete the city
    Then the city deletion response should be successful
    
    When they try to retrieve the deleted city
    Then the system should indicate that the city was not found
