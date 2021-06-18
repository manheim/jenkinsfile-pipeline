## [CredentialsPlugin](../src/CredentialsPlugin.groovy)

Enable this plugin to inject credentials into your stages using the [Jenkins Credentials Plugin](https://wiki.jenkins.io/display/JENKINS/Credentials+Plugin).

One-time setup:
* Install the [Jenkins Credentials Plugin](https://wiki.jenkins.io/display/JENKINS/Credentials+Plugin) on your Jenkins master.
* Define a credential that you want to inject.  Currently, only usernamePassword credentials are supported.

Specify the credential that you want to inject in your stages.  Optionally provide custom username/password environment variables that will contain the credential values for your use.

```
@Library('jenkinsfile-pipeline@<VERSION>') _

// MY_CREDENTIALS_USERNAME and MY_CREDENTIALS_PASSWORD will contain the username/password of the 'my-credentials' credential.

Jenkinsfile.init(this)

CredentialsPlugin.withCredentials('my-credentials').init()

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
