def call(body) {
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    configFilePath = config.configFilePath

    pipelineParams = readProperties file: "${configFilePath}"
    last_commit_id = sh (
        script: 'git rev-parse --short HEAD',
        returnStdout: true
    ).trim()
    pipelineParams['REVISION'] = last_commit_id

    return pipelineParams
}