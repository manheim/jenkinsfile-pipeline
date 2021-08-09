# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## Unreleased

- [Issue #103](https://github.com/manheim/jenkinsfile-pipeline/issues/103): BuildStage: Ability to customize build command
- [Issue #39](https://github.com/manheim/jenkinsfile-pipeline/issues/39): Allow pipeline to run on Dockerfile
- [Issue #44](https://github.com/manheim/jenkinsfile-pipeline/issues/44): ConfirmBeforeDeployPlugin: parameterize timeout
- [Issue #98](https://github.com/manheim/jenkinsfile-pipeline/issues/98): Change DeployStage ./bin/deploy.sh to rely on $ENVIRONMENT, instead of argument
- [Issue #96](https://github.com/manheim/jenkinsfile-pipeline/issues/96): Provide an environment variable containing the DeployStage environment name.
- [Issue #76](https://github.com/manheim/jenkinsfile-pipeline/issues/76): Allow DeployStage command to be customized
- [Issue #36](https://github.com/manheim/jenkinsfile-pipeline/issues/36): StashUnstash will now provide the artifact filename in an environment variable.
- [Issue #58](https://github.com/manheim/jenkinsfile-pipeline/issues/58): New DeleteDirPlugin to delete directories between runs
- [Issue #69](https://github.com/manheim/jenkinsfile-pipeline/issues/69): Turn node configuration into a default plugin.
- [Issue #88](https://github.com/manheim/jenkinsfile-pipeline/issues/88): Create default ScmPlugin to handle Scm tasks
- [Issue #67](https://github.com/manheim/jenkinsfile-pipeline/issues/67): Allow StashUnstashPlugin pattern to be defined in a file
- [Issue #85](https://github.com/manheim/jenkinsfile-pipeline/issues/85): Add convenience methods to get repo name and org name.
- [Issue #82](https://github.com/manheim/jenkinsfile-pipeline/issues/82): New EnvironmentVariablePlugin to add global environment variables
- [Issue #78](https://github.com/manheim/jenkinsfile-pipeline/issues/78): Replace TravisCI with GithubActions

## v1.0

### Added
- [Issue #10](https://github.com/manheim/jenkinsfile-pipeline/issues/10): Setup CI/CD for this project
- [Issue #13](https://github.com/manheim/jenkinsfile-pipeline/issues/13): Add a Code of Conduct
- [Issue #12](https://github.com/manheim/jenkinsfile-pipeline/issues/12): Add a Contributing guide
- [Issue #15](https://github.com/manheim/jenkinsfile-pipeline/issues/15): Add a License
- [Issue #1](https://github.com/manheim/jenkinsfile-pipeline/issues/1): Create a pipeline and BuildStage
- [Issue #21](https://github.com/manheim/jenkinsfile-pipeline/issues/21): Create a DeployStage
- [Issue #22](https://github.com/manheim/jenkinsfile-pipeline/issues/22): Update codecov bash script integration
- [Issue #27](https://github.com/manheim/jenkinsfile-pipeline/issues/27): Control the node where the pipeline is run
- [Issue #29](https://github.com/manheim/jenkinsfile-pipeline/issues/29): Allow access to Jenkins Credentials during BuildStage
- [Issue #31](https://github.com/manheim/jenkinsfile-pipeline/issues/31): Allow DeployStage to be decorated by Plugins
- [Issue #7](https://github.com/manheim/jenkinsfile-pipeline/issues/7): Save artifacts created in BuildStage
- [Issue #37](https://github.com/manheim/jenkinsfile-pipeline/issues/37): Pass environment name to DeployStage
- [Issue #2](https://github.com/manheim/jenkinsfile-pipeline/issues/2): Optionally wait for confirmation before DeployStage
- [Issue #41](https://github.com/manheim/jenkinsfile-pipeline/issues/41): Allow parameters from ParameterStore
- [Issue #46](https://github.com/manheim/jenkinsfile-pipeline/issues/46): Allow jenkins to assume a deployment role
- [Issue #51](https://github.com/manheim/jenkinsfile-pipeline/issues/51): Rename to jenkinsfile-pipeline
- [Issue #53](https://github.com/manheim/jenkinsfile-pipeline/issues/53): Extend CredentialsPlugin to apply during DeployStage
- [Issue #55](https://github.com/manheim/jenkinsfile-pipeline/issues/55): Add a strategy for reusing the same plugins across multiple pipelines
- [Issue #62](https://github.com/manheim/jenkinsfile-pipeline/issues/62): Make test objects available to exernal libraries
- [Issue #59](https://github.com/manheim/jenkinsfile-pipeline/issues/59): Make workflowScript available to plugins
- [Issue #65](https://github.com/manheim/jenkinsfile-pipeline/issues/65): Make it easy to access files from a plugin
- [Issue #61](https://github.com/manheim/jenkinsfile-pipeline/issues/61): Enable optional Declarative Pipelines
- [Issue #68](https://github.com/manheim/jenkinsfile-pipeline/issues/68): Enable select plugins by default
- [Issue #72](https://github.com/manheim/jenkinsfile-pipeline/issues/72): Incorrect Documentation: pipeline example image in README is wrong
