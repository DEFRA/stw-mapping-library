# STW Mapping Library

The Single Trade Window (STW) Mapping Library provides mappers between Trade UN/CEFACT JSON and
IPAFFS Notification JSON.

## Setup

Run `mvn install` to configure Git hooks. The linter runs on pre-commit and the secret scanner runs
on pre-push.

### Secret scanning

[TruffleHog](https://github.com/trufflesecurity/trufflehog) is used for secret scanning. It can be
installed using brew: `brew install trufflehog`.

## Running tests

The submodules have JUnit tests for unit and integration tests. All the tests can be run by running
`mvn test` from the root directory.
