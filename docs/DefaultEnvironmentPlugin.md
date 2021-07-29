## [DefaultEnvironmentPlugin](../src/DefaultEnvironmentPlugin.groovy)

This plugin is enabled by default.

At each DeployStage, make an environment variable available, containing the name of the DeployStage's environment.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

// DefaultEnvironmentPlugin.init() is called in Jenkinsfile.init(this)
Jenkinsfile.init(this)

def pipeline = new ScriptedPipeline()
def buildArtifact = new BuildStage()
// deployQa will have a the environment variable `ENVIRONMENT=qa` available
def deployQa = new DeployStage('qa')
// deployUat will have a the environment variable `ENVIRONMENT=uat` available
def deployUat = new DeployStage('uat')
// deployProd will have a the environment variable `ENVIRONMENT=prod` available
def deployProd = new DeployStage('prod')

pipeline.startsWith(buildArtifact)
        .then(deployQa)
        .then(deployUat)
        .then(deployProd)
        .build()
```

Optionally change the name of the variable provided.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

DefaultEnvironmentPlugin.withVariableName('CUSTOM_ENV')
// DefaultEnvironmentPlugin.init() is called in Jenkinsfile.init(this)
Jenkinsfile.init(this)

def pipeline = new ScriptedPipeline()
def buildArtifact = new BuildStage()
// deployQa will have a the environment variable `CUSTOM_ENV=qa` available
def deployQa = new DeployStage('qa')
// deployUat will have a the environment variable `CUSTOM_ENV=uat` available
def deployUat = new DeployStage('uat')
// deployProd will have a the environment variable `CUSTOM_ENV=prod` available
def deployProd = new DeployStage('prod')

pipeline.startsWith(buildArtifact)
        .then(deployQa)
        .then(deployUat)
        .then(deployProd)
        .build()
```


