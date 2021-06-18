## [ConfirmBeforeDeployPlugin](../src/ConfirmBeforeDeployPlugin.groovy)

By default, environments will deploy builds/changes automatically, with no human intervention.  Enable this plugin to optionally require confirmation before proceeding to deploy an environment.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

Jenkinsfile.init(this)

// Wait on confirmation before deploying to any environment.
ConfirmBeforeDeployPlugin.init()

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

You can configure different behavior per-environment, using the `autoDeploy` method.  Any environments set to `autoDeploy` will skip human confirmation, just as though the `ConfirmBeforeDeployPlugin` were not enabled.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

Jenkinsfile.init(this)

// Automatically deploy qa builds with no confirmation.
// Wait on confirmation before deploying to any other environment (uat/prod).
ConfirmBeforeDeployPlugin.autoDeploy('qa')
                         .init()

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
