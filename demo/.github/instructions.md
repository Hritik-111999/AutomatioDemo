<!-- Auto-generated guidance for AI coding agents working in this repository -->
# Repo-specific instructions (short & actionable)

Repository snapshot
- Java 21 (see `pom.xml` property `maven.compiler.release`)
- Maven-managed project located at the repo root (`pom.xml`).
- Main automation code lives in `src/main/java/demo/BrowserAutomation.java`.
- Minimal app class `src/main/java/demo/App.java` and a trivial test in `src/test/java/demo/AppTest.java`.

How this project is structured and why it matters
- Single-module Maven project. Keep changes compatible with the `pom.xml` (do not change Java release or dependency coordinates without reason).
- `BrowserAutomation.java` is an end-to-end Selenium script designed to run from a main method. It uses:
  - Selenium (`org.seleniumhq.selenium:selenium-java`),
  - WebDriverManager (`io.github.bonigarcia:webdrivermanager`) for driver handling,
  - explicit waits via `WebDriverWait` and `ExpectedConditions`, plus frequent Thread.sleep() calls.
- The file uses many XPath locators (examples: `waitAndLocate("//input[@id='name']", ...)`) and helper wrappers like `waitAndLocate`, `sendKeysWithVerification`, and `clickWithVerification`. When modifying element lookups, prefer changing or reusing these helpers.

Developer workflows (concrete commands)
- Build the project: mvn -B clean package
- Run tests: mvn test
- Run a single unit test: mvn -Dtest=AppTest test
- Running the Selenium `main`:
  - Preferred: run `BrowserAutomation` from an IDE (ensures dependencies and drivers are available).
  - Alternative: add the Maven exec plugin or run via a runner that provides Selenium/WebDriver binaries; currently no exec plugin is configured in `pom.xml`.

Patterns and conventions to preserve
- Tests currently use JUnit 4 (see `AppTest.java`) while `pom.xml` also includes TestNG. Do not migrate test frameworks without confirming intent—both frameworks appear intentionally available.
- Keep Java 21 compatibility (`maven.compiler.release` = 21).
- Synchronous verification pattern: after sendKeys, code waits for the element `value` attribute to equal the typed string (method `sendKeysWithVerification`). New code that fills fields should reuse that helper to keep behavior consistent.
- Element selection style: the project relies heavily on XPath. If switching to CSS selectors, update the helper functions or add dual-locator support rather than changing every call site.
- Exception handling: BrowserAutomation wraps many errors into RuntimeException or prints to stderr; when adding new logic, follow the same style (catch, wrap or log) unless creating a separate library API.

Integration points and external dependencies
- Selenium + WebDriverManager (drivers). Tests and automation expect a graphical environment or a configured headless driver. The project does not set headless mode by default (ChromeOptions are created but not customized).
- File I/O for tests: `BrowserAutomation` creates a temporary test file in the user's home directory for upload tests. Be careful when editing file paths or cleanup logic.

Quick examples (copyable) from this repo
- Wait-and-locate an element (use existing helper):
  - waitAndLocate("//input[@id='name']", "name field")
- Enter text and verify value:
  - sendKeysWithVerification(nameElement, "KingOOstring", "name")
- Click with scrolling and verification:
  - clickWithVerification(someElement, "submit button")

When to ask the maintainer before changing things
- Changing the Java release or major dependency versions (e.g., Selenium/WebDriverManager).
- Replacing the test framework (JUnit/TestNG) or changing surefire config.
- Removing or heavily refactoring `BrowserAutomation.java` main flow—this is the primary automation script.

What an AI agent should do first when asked to modify tests or automation
1. Reproduce locally: run `mvn test` and, if editing BrowserAutomation, run it from IDE or add a temporary `exec` plugin entry.
2. Search for helper methods in `BrowserAutomation.java` (e.g., `waitAndLocate`, `sendKeysWithVerification`) and extend/reuse them instead of duplicating behavior.
3. When adding new element interactions prefer XPath consistent with existing calls or add a small adapter helper that supports both XPath and CSS.

If anything in these instructions is unclear, tell me which part (build, running BrowserAutomation, or locator patterns) you'd like me to expand or adjust.
