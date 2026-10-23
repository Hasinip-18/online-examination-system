pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

environment {
    DOCKER_IMAGE = 'phasini/online-examination-system:1.0'
    DOCKER_BIN = '/Users/hasinipanjala/.docker/bin/docker'
}

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo 'Source code checked out successfully'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn -B test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn -B package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    set -e
                    export PATH="/Users/hasinipanjala/.docker/bin:/Applications/Docker.app/Contents/Resources/bin:$PATH"
                    "$DOCKER_BIN" version
                    "$DOCKER_BIN" build -t "$DOCKER_IMAGE" .
                '''
            }
        }

        stage('Verify Docker Image') {
            steps {
                sh '''
                    set -e
                    export PATH="/Users/hasinipanjala/.docker/bin:/Applications/Docker.app/Contents/Resources/bin:$PATH"
                    "$DOCKER_BIN" image inspect "$DOCKER_IMAGE" > /dev/null
                    echo "Docker image verified successfully"
                '''
            }
        }
                stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-credentials',
                    usernameVariable: 'DOCKERHUB_USER',
                    passwordVariable: 'DOCKERHUB_TOKEN'
                )]) {
                    sh '''
                        set -e
                        export PATH="/Users/hasinipanjala/.docker/bin:/Applications/Docker.app/Contents/Resources/bin:$PATH"

                        echo "$DOCKERHUB_TOKEN" | "$DOCKER_BIN" login \
                            --username "$DOCKERHUB_USER" \
                            --password-stdin

                        "$DOCKER_BIN" push "$DOCKER_IMAGE"

                        "$DOCKER_BIN" logout
                        echo "Docker image pushed successfully"
                    '''
                }
            }
        }
        stage('Check Kubernetes Access') {
            steps {
                sh '''
                    set -e
                    export PATH="/Users/hasinipanjala/.docker/bin:/Applications/Docker.app/Contents/Resources/bin:/opt/homebrew/bin:$PATH"

                    echo "Checking kubectl..."
                    kubectl version --client

                    echo "Checking Kubernetes context..."
                    kubectl config current-context

                    echo "Checking Kubernetes nodes..."
                    kubectl get nodes
                '''
            }
        }
    }

    post {
        success {
            echo 'BUILD AND DOCKER IMAGE CREATION SUCCESSFUL'
        }

        failure {
            echo 'BUILD OR DOCKER IMAGE CREATION FAILED'
        }

        always {
            echo 'Pipeline execution completed'
        }
    }
}
