def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def docker_image_name = config.explicitDockerImageName
    def docker_image_version = config.dockerImageVersion

    docker.withRegistry('https://docker-registry-exapmle.com', 'DockerCredentialsID') {
        def dockerImage = docker.image("${docker_image_name}:${docker_image_version}")
        dockerImage.pull()    
    }
    withEnv(["DOCKER_IMAGE_NAME=${docker_image_name}","DOCKER_IMAGE_TAG=${docker_image_version}"]){
        sh '''
            docker run ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}
        '''
    }
}