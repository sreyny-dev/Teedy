pipeline {
    agent any
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B clean package' 
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('PMD') {
            steps {
                sh 'mvn pmd:pmd'
            }
            post {
                always {
                    archiveArtifacts artifacts: '**/target/site/**', fingerprint: true
                }
            }
        }
        stage('Generate Javadoc') {
            steps {
                sh 'mvn javadoc:javadoc'
            }
            post {
                always {
                    archiveArtifacts artifacts: '**/target/site/apidocs/**', fingerprint: true
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/site/**', fingerprint: true
            archiveArtifacts artifacts: '**/target/**/*.jar', fingerprint: true
            archiveArtifacts artifacts: '**/target/**/*.war', fingerprint: true
        }
    }
}
