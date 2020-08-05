def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    if (!fileExists("target")) {
        echo "Skipping docker build , target folder is not exist on project"
        return
    }
    
    def docker_image_name = config.explicitDockerImageName
    def docker_image_version = config.dockerImageVersion
    
    docker.withRegistry('https://docker-registry-exapmle.com', 'DockerCredentialsID') {
        def dockerImage = docker.build("${docker_image_name}:${docker_image_version}")
        dockerImage.push()    
    }
}