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

Environment variables can be lazy-loaded, if they won't be present at the time that you configure your plugin, but *will* be available by the time they're used.  This can be helpful when moving configuration to a Customizations class, where you might not have immediate access to all of a project's information.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

Jenkinsfile.init(this)
// The project Scm hasn't been checked out just yet, but will be checked out when the pipeline runs
EnvironmentVariablePlugin.add('MY_REPO') { ScmUtil.getRepositoryName() }.init()

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
