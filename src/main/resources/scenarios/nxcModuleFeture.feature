@nXcWebApp
Feature: Demo feature of Nxc Web Pge
  #Sample Test Scenario Description

  @nxcWebApp_1
  Scenario: NXCWeb Example Normal
    Given I launched Nxc Web url
    When I login with username "John" and password "Perfecto1"
    Then I should see home page

  @nxcWebApp_2
  Scenario Outline: NXCWeb Example Data Driven
    Given I launched Nxc Web url
    When I login with username "<username>" and password "<password>"
    Then I should see home page
    
    Examples: 
	| username 	| password   |
	|John|Perfecto1|
	|John|Perfecto|
	
	
  @nxcWebApp_3
  Scenario: NXCWeb Example Xlsx File
    Given I launched Nxc Web url
    When I login with username "<username>" and password "<password>"
    Then I should see home page
    
   #Examples: {'datafile' : 'src/main/resources/data/testdata.xlsx'}
    
   @nxcWebApp_4
  Scenario: NXCWeb Example Xlsx File custom
    Given I launched Nxc Web url
    When I login with valid username and password
    Then I should see home page
    
  @nxcWebApp_5
  Scenario: User Login scenario
    Given I launched Nxc Web url
    When User login with the following username and password with data in excel at "src/main/resources/data/InputData.xlsx"
    
    
   @nxcWebApp_6
  Scenario: NXCWeb Example data table
    When I login with valid username and password with datatable
    |John|Perfecto|
	|John|Perfecto1|
	
	
   @nxcWebApp_7
  Scenario: NXCWeb Example data table with maps
    When I login with valid username and password with datatable and maps
    |username|password|
    |John|Perfecto|
	|John|Perfecto1|
	

   @nxcWebApp_8
  Scenario: NXCWeb Example Xlsx File custom with my dataparser
    Given Validate logins with given data at "src/main/resources/data/InputData.xlsx"
    
  @nxcWebApp_9
  Scenario Outline: NXCWeb Example Xlsx File custom with my dataparser
    Given Validate logins with given data at "<row_index>" in "src/main/resources/data/InputData.xlsx"
    
  Examples:
    |row_index|
    |1|
    |2|
    |3|
    |4|
    |5|
    |6|
