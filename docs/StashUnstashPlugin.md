## [StashUnstashPlugin](../src/StashUnstashPlugin.groovy)

Enable this plugin to [stash](https://www.jenkins.io/doc/pipeline/steps/workflow-basic-steps/#stash-stash-some-files-to-be-used-later-in-the-build) your artifact at the end of your BuildStage, and [unstash](https://www.jenkins.io/doc/pipeline/steps/workflow-basic-steps/#unstash-restore-files-previously-stashed) it to make it available at the start of each DeployStage.

Specify the path to the artifact that you want to stash.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

// BuildStage is expected to generate a file target/MyApplication.war - stash it on completion
// Unstash MyApplication.war at the beginning of each subsequent DeployStage
StashUnstashPlugin.withArtifact('target/MyApplication.war').init()

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
