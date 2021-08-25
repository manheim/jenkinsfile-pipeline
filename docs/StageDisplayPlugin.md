## [StageDisplayPlugin](../src/StageDisplayPlugin.groovy)

This plugin is enabled by default.

Wraps each Stage with Jenkinsfile DSL `stage`, and determines the name displayed by each Stage.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

// StageDisplayPlugin.init() is called in Jenkinsfile.init(this)
// This wraps your pipeline Stages with Jenkinsfile stage { }
Jenkinsfile.init(this)

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

This plugin can be disabled, and is useful for DeclarativePipelines that use templates to determine stage names.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

StageDisplayPlugin.disable()
Jenkinsfile.init(this)

def pipeline = new DeclarativePipeline()
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
