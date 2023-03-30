### Remarks

* The exercise is far too long. In order to demonstrate some capability, you need to work for more than 2 or 3 hours. Adding just one new API and rendering the data provided by that API would be a reasonable test of capability.
* I worked for approximately 7 hours.
* The project has dependencies in it which are more than 7 years old. It also uses username / password authentication (as opposed to JWT), which you would rarely use in the real world. More than half of my time was spent:
  * Debugging issues with dependencies.
  * Remembering how to use legacy frameworks such as JUnit4.
  * Finding documentation to make integration tests work with the legacy version of Spring Boot.
  * Understanding the unusual security configuration.
* None of the above would be too much trouble for business as usual development but for an aptitude test it is too much.
* My guess is that this codebase might be reflective of the production codebase, which raises alarm bells. It would require modernisation as a matter of some urgency, to ensure that it continues to be possible to hire good engineers to work on it. I would want to understand how well the business understands this problem, should I asked be asked to attend a further interview.
* There was not time to write tests for the angular layer. I would like to have explored this.