# Contributing to jenkinsfile-pipeline-library

Thanks for taking the time to contribute!

The following is a set of guidelines for contributing to the jenkinsfile-pipeline-library on GitHub. These are mostly guidelines, not rules. Use your best judgment, and feel free to propose changes to this document in a pull request.

## Code of Conduct

This project and everyone participating in it is governed by our [Code of Conduct](https://github.com/manheim/jenkinsfile-pipeline-library/blob/master/CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code.

## What should I know before I get started?

This library is an abstraction built on the [Jenkinsfile Pipeline](https://www.jenkins.io/doc/book/pipeline/).  Rather than writing a pipeline from scratch using the Jenkinsfile pipeline DSL, this library instead enables you to express the high-level steps that deliver your code to your customers hands.  The low-level details of those steps should be implemented as Plugins, and changing those details should be as easy as toggling Plugins on and off.

This project is similar to, and a more generic implementation of [terraform-pipeline](https://github.com/manheim/terraform-pipeline).  Many of the design decisions that went into terraform-pipeline will be applied to this library.  Learn more in the [wiki](https://github.com/manheim/terraform-pipeline/wiki).  The intent is to eventually deprecate and replace terraform-pipeline with this library.

## How Can I Contribute?

1.  Create an [Issue](https://github.com/manheim/jenkinsfile-pipeline-library/issues) for the change that's being made.
2.  All changes pending the next release will be staged in the master branch.
3.  Fork this project.
4.  Make a branch in your fork, named after your issue. Ex: "issue_152"
5.  Make your changes in the branch and run the tests and codestyle checks with with `./gradlew check --info`
6.  Update the CHANGELOG with your changes. Changes are queued under "Unreleased", until an official release is cut.
7.  Validate your changes by pointing a terraform-pipeline project to your fork's branch, and run an actual pipeline.
8.  Make a PR against the master branch of this project, and reference your Issue in the PR.
9.  Your PR will be reviewed and merged into master.
10.  Changes in master will be periodically grouped and published as a Release.

## Styleguides

Code style is enforced as part of the build, and can be run using `./gradlew check --info`.  For details, you can see the configuration in [./config/codenarc/codenarc.xml](https://github.com/manheim/jenkinsfile-pipeline-library/blob/master/config/codenarc/codenarc.xml)

## Attribution

This contribution guide is unrelated to, but built from the [Atom Contributing Guide](https://github.com/atom/atom/blob/master/CONTRIBUTING.md).
