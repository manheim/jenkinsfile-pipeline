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

Environment variables can be lazy-loaded, if they won't be present at the time that you configure your plugin, but *will* be available at the time that they'll be used.  Use this feature if you want to combine EnvironmentVariablePlugin with other plugins that may also set environment variables.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

Jenkinsfile.init(this)
// The value of `env "OTHER_VAR"` does not exist right now, but will exist at the time of deployment
EnvironmentVariablePlugin.add('MY_VAR') { env "OTHER_VAR" }.init()

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
