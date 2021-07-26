## [NodePlugin](../src/NodePlugin.groovy)

This plugin is enabled by default.

This plugin controls where jobs are run.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

// NodePlugin.init() is called in Jenkinsfile.init(this)
// This wraps your pipeline Stages with Jenkinsfile node { }
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

You can configure the label of the node that your pipeline runs on.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

NodePlugin.withLabel('myNodeLabel')

// NodePlugin.init() is called in Jenkinsfile.init(this), and wraps your pipeline Stages with node('myNodeLabel') { }
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
