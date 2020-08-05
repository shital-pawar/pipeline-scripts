def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    mavenLabel=config.mavenLabel
	jdkLabel=config.jdkLabel
    mvnBuildPhase=config.mvnBuildPhase ? config.mvnBuildPhase : 'install'

    withMaven( maven: "${mavenLabel}", jdk: "${jdkLabel}" ) {
        sh '''
			mvn clean ${MAVEN_BUILD_PHASE}
		'''
    }
}