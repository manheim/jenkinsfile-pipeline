## [StashUnstashPlugin](../src/StashUnstashPlugin.groovy)

Enable this plugin to [stash](https://www.jenkins.io/doc/pipeline/steps/workflow-basic-steps/#stash-stash-some-files-to-be-used-later-in-the-build) your artifact at the end of your BuildStage, and [unstash](https://www.jenkins.io/doc/pipeline/steps/workflow-basic-steps/#unstash-restore-files-previously-stashed) it to make it available at the start of each DeployStage.

Specify the path to the artifact that you want to stash.  The stashed filename will be made available in DeployStages with the environment variable `BUILD_ARTIFACT`.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

Jenkinsfile.init(this)

// BuildStage is expected to generate a file target/MyApplication.war - stash it on completion
// Unstash MyApplication.war at the beginning of each subsequent DeployStage.
StashUnstashPlugin.withArtifact('target/MyApplication.war').init()

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

Alternatively, you can reduce the amount of configuration necessary, by providing the artifact pattern in a file named `.buildArtifact`.  This is useful when reusing your Plugin configurations in a Customizations class.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

Jenkinsfile.init(this)

// BuildStage is expected to generate an artifact - this file will be stashed
// The file will be identified by the pattern provided by a `.buildArtifact` file
// Unstash the artifact at the beginning of each subsequent DeployStage
StashUnstashPlugin.init()

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

You can also override the `.buildArtifact` file with a filename of your choice.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

Jenkinsfile.init(this)

// BuildStage is expected to generate an artifact - this file will be stashed
// The file will be identified by the pattern provided by a `.customArtifactPattern` file
// Unstash the artifact at the beginning of each subsequent DeployStage
StashUnstashPlugin.withArtifactFrom('.customArtifactPattern').init()

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

You can also override the `BUILD_ARTIFACT` variable, with one of your choice.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

Jenkinsfile.init(this)

// BuildStage is expected to generate an artifact - this file will be stashed
// Unstash the artifact at the beginning of each subsequent DeployStage, and
// set a CUSTOM_BUILD_ARTIFACT environment variable to identify the stashed file.
StashUnstashPlugin.withUnstashVariable('CUSTOM_BUILD_ARTIFACT').init()

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

