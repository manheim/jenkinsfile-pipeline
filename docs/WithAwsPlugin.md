## [WithAwsPlugin](../src/WithAwsPlugin.groovy)

Enable this plugin to manage AWS Authentication with the [pipeline-aws-plugin](https://github.com/jenkinsci/pipeline-aws-plugin).

One-time setup

* Have the [pipeline-aws-plugin](https://github.com/jenkinsci/pipeline-aws-plugin) installed on your Jenkins instance.
* (Optional) Define an AWS_ROLE_ARN variable, or environment-specific `${env}_AWS_ROLE_ARN`

Example pipeline using the WithAwsPlugin using a single default role:

```
// Define the AWS_ROLE_ARN environment variable
WithAwsPlugin.init()

def pipeline = new ScriptedPipeline(this)
def buildArtifact = new BuildStage()
// Assume AWS_ROLE_ARN before deploying each environment
def deployQa = new DeployStage('qa')
def deployUat = new DeployStage('uat')
def deployProd = new DeployStage('prod')

pipeline.startsWith(buildArtifact)
        .then(deployQa)
        .then(deployUat)
        .then(deployProd)
        .build()
```

Example pipeline using environment-specific roles:

```
// Define environment-specific variables (eg: QA_AWS_ROLE_ARN, UAT_AWS_ROLE_ARN, PROD_AWS_ROLE_ARN)
WithAwsPlugin.init()

def pipeline = new ScriptedPipeline(this)
def buildArtifact = new BuildStage()

// Assume QA_AWS_ROLE_ARN before deploying, or fall back to AWS_ROLE_ARN
def deployQa = new DeployStage('qa')

// Assume UAT_AWS_ROLE_ARN before deploying, or fall back to AWS_ROLE_ARN
def deployUat = new DeployStage('uat')

// Assume PROD_AWS_ROLE_ARN before deploying, or fall back to AWS_ROLE_ARN
def deployProd = new DeployStage('prod')

pipeline.startsWith(buildArtifact)
        .then(deployQa)
        .then(deployUat)
        .then(deployProd)
        .build()
```
