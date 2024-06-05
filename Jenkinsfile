pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        script {
          git 'https://github.com/ArinaNichko/at-advanced-program/'
        }
      }
    }
    stage('Build Multi stage Docker Image') {
      steps {
        script {
          bat "mvn clean install -DsuiteXmlFile=src/test/resources/runner/feature.xml -Dmaven.test.failure.ignore=true clean package"
        }
      }
    }
   stage('Build Multi stage Docker Image') {
      steps {
        script {
            withSonarQubeEnv() {
      bat "mvn clean verify sonar:sonar -Dsonar.projectKey=automation-advanced -Dsonar.projectName=automation-advanced"
        }
      }
    }
   }
  }
}
