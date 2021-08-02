## [ConfirmBeforeDeployPlugin](../src/ConfirmBeforeDeployPlugin.groovy)

This plugin is enabled by default.

At each DeployStage, require confirmation before proceeding to deploy the environment.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

// ConfirmBeforeDeployPlugin.init() is called in Jenkinsfile.init(this)
// The plugin requires a human to confirm before deploying qa, uat, prod
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

By default, the plugin will timeout after 15 minutes if no answer is given, and your pipeline will be aborted.  You can optionally change this timeout.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

// Change timeout to 48 hours
ConfirmBeforeDeployPlugin.withTimeout(48, 'HOURS')

// ConfirmBeforeDeployPlugin.init() is called in Jenkinsfile.init(this)
// The plugin requires a human to confirm before deploying qa, uat, prod
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


You can configure different behavior per-environment, using the `autoDeploy` method.  Any environments set to `autoDeploy` will skip human confirmation, just as though the `ConfirmBeforeDeployPlugin` were not enabled.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

// Automatically deploy qa builds with no confirmation.
// Wait on confirmation before deploying to any other environment (uat/prod).
ConfirmBeforeDeployPlugin.autoDeploy('qa')

// ConfirmBeforeDeployPlugin.init() is called in Jenkinsfile.init(this)
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
