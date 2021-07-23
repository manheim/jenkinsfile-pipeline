## [EnvironmentVariablePlugin](../src/EnvironmentVariablePlugin.groovy)

Enable this plugin to add environment variables across your pipeline stages.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

Jenkinsfile.init(this)
EnvironmentVariablePlugin.add('MY_VAR', 'someValue').init()

// The environment variable MY_VAR=someValue is made available across BuildStage and DeployStages
def pipeline = new ScriptedPipeline()
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
