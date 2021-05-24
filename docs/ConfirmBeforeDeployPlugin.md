## [ConfirmBeforeDeployPlugin](../src/ConfirmBeforeDeployPlugin.groovy)

By default, environments will deploy builds/changes automatically, with no human intervention.  Enable this plugin to optionally require confirmation before proceeding to deploy an environment.

```
@Library('jenkinsfile-pipeline-library@<VERSION>') _

// Continue to deploy qa builds with no confirmation.
// Wait on confirmation before deploying to any other environment.
ConfirmBeforeDeployPlugin.autoDeploy('qa')
                         .init()

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
