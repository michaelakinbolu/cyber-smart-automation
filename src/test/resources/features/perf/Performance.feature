@perf
Feature: Performance Test

  Scenario: Performance Test
    Given I have the "google.jmx" test in my script directory
    When the test script "google.jmx" is executed
    Then the report is generated correctly with following average,error rate,90th percentile and 95th percentile
      | Transaction    | Average   | ErrorRate | agg90th  | agg95th  | Throughput |
      | 001_HomePage   | 10000     | 1         | 15000    | 20000    | 0          |
      | 002_Search     | 10000     | 1         | 15000    | 20000    | 0          |