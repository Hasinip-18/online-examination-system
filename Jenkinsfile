pipeline {
agent any
tools {
    jdk 'JDK17'
    maven 'Maven3'
}

environment {
    DOCKER_IMAGE = 'online-examination-system:1.0'
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
                "$DOCKER_BIN" version
                "$DOCKER_BIN" build -t "$DOCKER_IMAGE" .
            '''
        }
    }

    stage('Verify Docker Image') {
        steps {
            sh '''
                set -e
                "$DOCKER_BIN" image inspect "$DOCKER_IMAGE" > /dev/null
                echo "Docker image verified successfully"
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
