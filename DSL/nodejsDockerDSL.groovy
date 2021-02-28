job('Aplicacion Node.js Docker DSL') {
    description('Aplicación Node JS Docker DSL para el curso de Jenkins')
    scm {
        git('https://github.com/gerard300/node-js-app-docker.git', 'main') { node ->
            node / gitConfigName('gerard300')
            node / gitConfigEmail('gerardeos300@gmail.com')
        }
    }
    triggers {
        scm('H/7 * * * *')
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('gerard300/nodejsapp')
            tag('${GIT_REVISION,length=7}')
            registryCredentials('gerard300')
            forcePull(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
    publishers {
	slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
        }
    }
}
