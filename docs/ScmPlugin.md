## [ScmPlugin](../src/ScmPlugin.groovy)

This plugin is enabled by default.

Checkout the project's code.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

// ScmPlugin.init() is called in Jenkinsfile.init(this)
// The plugin will checkout the project's code so that it can be used by your pipeline Stages.
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

