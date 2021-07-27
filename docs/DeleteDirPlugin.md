## [DeleteDirPlugin](../src/DeleteDirPlugin.groovy)

Enable this plugin to run [deleteDir](https://www.jenkins.io/doc/pipeline/steps/workflow-basic-steps/#deletedir-recursively-delete-the-current-directory-from-the-workspace) between Stages, and recursively delete the current directory from the workspace.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

Jenkinsfile.init(this)

// Call `deleteDir` and resurively delete the current directory from the workspace, at the start of each Stage.
DeleteDirPlugin.init()

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

Alternatively, delete a specific directory.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

Jenkinsfile.init(this)

// Call `dir('target') { deleteDir }`, and recursively delete the target directory from the workspace, at the start of each Stage.
DeleteDirPlugin.withDir('target').init()

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
