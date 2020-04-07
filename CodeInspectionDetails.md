**Logistics**:

  1. When will a formal code inspection be warranted?



*   Each pull request for a feature branch will warrant a formal code inspection.
*   The staging branch once approved will be merged to the master branch.
*   Code inspection will be conducted by the team members who did not work on the feature branch.

  2. Who will take the lead on moderating inspections?

*   Tyson will make sure that everyone follows the code inspection processes correctly.

  3. How will you share the results of inspections?



*   We will use pull requests to add reviewers and let the reviewers leave comments on the pull request as needed.

**Criteria**:

  4. What are the key "code smells" from each chapter of Clean Code? **This is the big question.** Chapter 17 of Clean Code might help you recall them.



*   Comments:
    *   Inappropriate Information (C1)
    *   Redundant Comment (C3)
    *   Poorly Written Comment (C4)
    *   Commented-Out Code (C5)
*   Environment:
    *   Tests Require More Than One Step (E2)
*   Functions:
    *   Too Many Arguments (F1)
    *   Output Arguments (F2)
*   General:
    *   Duplication (G5)
    *   Inconsistency (G11)
    *   Feature Envy (G14)
    *   Use Explanatory Variables (G19)
    *   Function names should say what they do (G20)
    *   Prefer Polymorphism to If/Else or Switch/Case (G23)
    *   Replace Magic Numbers with Named Constants (G25)
    *   Encapsulate Conditionals (G28)
    *   Functions should do one thing (G30)
*   Names:
    *   Choose Descriptive Names (N1)
    *   Use Unambiguous Names (N4)
    *   Names Should Describe Side-Effects (N7)
*   Tests:
    *   Insufficient Tests (T1)
    *   Don’t Skip Trivial Tests (T3)
    *   Test Boundary Conditions (T5)
    *   Exhaustively Test Near Bugs (T6)
    *   Tests Should be Fast (T9)

  5. Will everyone apply all criteria from every chapter from Clean Code? Or will each person specialize in a few criteria?



*   The criterias to check for may be divided amongst the team members running the code inspection but this still requires each team member to be knowledgeable in each criteria.

**Scope:**

  6. Will your team inspect every file in your codebase? Every file you touch in your feature branch? Or something else entirely?



*   We will look at each of the files that are changed in the feature branch.

  7. Of those files, will each person look at every file in consideration? Or will your team assign different files to different people?



*   Everyone will look at each of the files that are changed.

**Tools**:

  8. To what extent can your inspection criteria be automated? Automation will increase your inspection's speed and reliability.



*   If there are failing JUnit tests then code inspection for merging will not be considered until those tests pass. This will also be done with Gradle.
*   Every time a push is made to any branch in the repository, the GitHub workflow will run all Junit tests on that branch ensuring that all tests pass before building the project for deployment.

  9. Which aspects of your inspection criteria will need human intervention?



*   Checking for bad code smells.
