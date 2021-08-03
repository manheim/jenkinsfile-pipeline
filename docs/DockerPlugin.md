## [DockerPlugin](../src/DockerPlugin.groovy)

Enable this plugin to run your pipeline with [Docker](https://www.jenkins.io/doc/book/pipeline/docker/).

By default, the plugin will build and load a Dockerfile that you've provided in the root folder of your project.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

// Build and load a Dockerfile that you've provided in your project
DockerPlugin.init()
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

