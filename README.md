[![Build Status](https://travis-ci.org/manheim/jenkinsfile-pipeline-library.svg?branch=master)](https://travis-ci.org/manheim/jenkinsfile-pipeline-library) [![codecov](https://codecov.io/gh/manheim/jenkinsfile-pipeline-library/branch/master/graph/badge.svg)](https://codecov.io/gh/manheim/jenkinsfile-pipeline-library)


# jenkinsfile-pipeline-library
A reusable pipeline template to build and deploy an application serially across multiple environments, using [Jenkins](https://www.jenkins.io/) and [Jenkinsfile](https://www.jenkins.io/doc/book/pipeline/jenkinsfile/).

# Requirements and Assumptions
1.  Your application and deployment process is available in a github repo.
2.  You have an application that can be built in some way.  By default, you can provide a `./bin/build.sh` to build your artifact.
3.  Your application artifact can be deployed to an environment in some way.  By default, you can provide a `./bin/deploy.sh` to deploy your artifact.
4.  You've imported jenkinsfile-pipeline-library to your Jenkins instance.
![Importing Pipeline Library](./images/import-jenkinsfile-pipeline-library.png)

# How to Use
1.  Create a Jenkinsfile in your application project and import the [version](https://github.com/manheim/jenkinsfile-pipeline-library/releases) of jenkinsfile-pipeline-library that you want to use.  It's recommended that you always use the latest version.
```
// Jenkinsfile
@Library('jenkinsfile-pipeline-library@<VERSION>') _
```
2.  Start your pipeline by building your deployment artifact.
```
// Jenkinsfile
...
def buildArtifact = new BuildStage()
```
3.  Create a DeployStages for each of the environments that you would normally deploy to.  This example deploys to qa, uat, and prod environments.  The number and names of your environments can differ from this example.  Choose the environments and environment names that reflect your own development process to go from Code to Customer.
```
// Jenkinsfile
...
def deployQa = new DeployStage('qa')
def deployUat = new DeployStage('uat')
def deployProd = new DeployStage('prod')
```
4.  Link the Stages together in the order that you want them to run.  This examples builds your deployment artifact, then deploys qa, then uat, then prod.  Each step *MUST* succeed before it can proceed on to the next.
```
// Jenkinsfile
...
buildArtifact.then(deployQa)
             .then(deployUat)
             .then(deployProd)
```
5.  The design of this library is influenced by the [Builder Pattern](https://en.wikipedia.org/wiki/Builder_pattern) - your pipeline has been configured, but hasn't been constructed just yet.  Finalize and create your pipeline by calling the `build()` method.  This should only be done once - no code should come after calling this method.
```
// Jenkinsfile
...
        .build()
```

7.  From beginning to end, your Jenkinsfile should roughly look like this:

```
@Library('jenkinsfile-pipeline-library@<VERSION>') _

def buildArtifact = new BuildStage()
def deployQa = new DeployStage('qa')
def deployUat = new DeployStage('uat')
def deployProd = new DeployStage('prod')

buildArtifact.then(deployQa)
             .then(deployUat)
             .then(deployProd)
             .build()
```

8.  Load your project into Jenkins, and point it to your newly created Jenkinsfile.
    1. Create a new job using the 'New Item' menu

       ![New Item](./images/NewItem.png)
    2. Select Multibranch Pipeline after naming your new job

       ![Multibranch Pipeline](./images/MultibranchPipeline.png)
    3. Configure your new job, and point it to the location of your source code.

       ![Loading/Configuring Project](./images/configure-project.png)

9.  If everything was successful, you should see something like this:

![DefaultPipelineSuccess](./images/default-pipeline-success.png)

# Goals that this library is trying to achieve:

1.  Application code should be written once, and should be reusable for all environments.
    1.  Configuration, especially environment-specific configuration, should be externalized.  Where it's externalized to should be irrelevant.
2.  As much as possible, a pipeline should reflect what teams do to get from Code to Customer - no more, but also no less.
    1.  Allow teams to specify their own steps in that process, and teams should choose how those steps are linked together.
    2.  This is an OpenSource project - defaults should make reasonable sense for anyone that happens to use this project.
3.  Apply the [Open/Closed Principle](https://en.wikipedia.org/wiki/Open%E2%80%93closed_principle)
    1.  You should rarely need to change the base code, everything should be open for extension without modification to the base code.
    2.  It should be possible to add behaviors through Plugins and Decorations - this makes behavior addable/removable/extenable.
    3.  Think of plugins as interchangeable Lego pieces - you can swap one piece out for another, without fundamentally altering what it is to be a Pipeline.
4.  There should only be one way to Production, and that way should be crystal clear.
    1.  The master branch (or its equivalent) is the one-and-only way to Production.

