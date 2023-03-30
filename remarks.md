### Remarks

* The exercise is too long. In order to demonstrate capability, you need to work for more than 2 or 3 hours. Adding just one new API and rendering the data provided by that API might be a more reasonable test of capability. I worked for roughly 7 hours.
* The project has libraries in it which are very old. It uses username / password authentication (rather than the more commonplace JWT). More than half of my time was spent:
  * Debugging issues with dependencies.
  * Remembering how to use legacy frameworks such as JUnit4.
  * Finding documentation to make integration tests work with the legacy version of Spring Boot.
  * Understanding the unusual security configuration.
* All the above would be fine for BAU development but for an aptitude test it adds unnecessary complexity.
* My guess is that this codebase might be similar to the production codebase, which raises concerns. It would require modernisation as a matter of urgency, to ensure that it continues to be possible to hire strong engineers to work on it. I would want to understand how well the business understands this requirement, should I be asked asked to attend a further interview.
* There was no time to add tests for the angular layer. I would like to have focused on this.