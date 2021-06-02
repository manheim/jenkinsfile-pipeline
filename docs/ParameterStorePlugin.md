## [ParameterStorePlugin](../src/ParameterStorePlugin.groovy)

Enable this plugin to inject environment variables into your stages using the [AWS Parameter Store Build Wrapper Plugin](https://plugins.jenkins.io/aws-parameter-store/).

One-time setup:
* Install the [AWS Parameter Store Build Wrapper Plugin](https://plugins.jenkins.io/aws-parameter-store/).
* If your environment live in different AWS Accounts, create an AWS Credential with the id `<ENVIRONMENT>_PARAMETER_STORE_ACCESS` that provides access to ParameterStore for that environment's account.

By default, parameters will be retrieved from the ParameterStore path constructed from your project's Git Organization, Git Repository name, and environment. Eg: If my repo were at https://github.com/Manheim/MyApp, then my 'qa' environment would receive parameters from the ParameterStore path `/Manheim/MyApp/qa`.

By default, [AWS Parameter Store Build Wrapper Plugin](https://plugins.jenkins.io/aws-parameter-store/) will be set to use `basename`, and all of your parameter names will be [converted and uppercased](https://github.com/jenkinsci/aws-parameter-store-plugin/blob/master/src/main/java/hudson/plugins/awsparameterstore/AwsParameterStoreService.java#L309-L315).  Eg: `/Manheim/MyApp/qa/param-1` becomes the environment variable `PARAM_1`.

```
@Library('jenkinsfile-pipeline-library@<VERSION>') _

ParameterStorePlugin.init()

def pipeline = new ScriptedPipeline(this)
def buildArtifact = new BuildStage()
def deployQa = new DeployStage('qa')
def deployUat = new DeployStage('uat')
def deployProd = new DeployStage('prod')

pipeline.startsWith(buildArtifact)
        .then(deployQa)
        .then(deployUat)
        .then(deployProd)
        .build()
```
