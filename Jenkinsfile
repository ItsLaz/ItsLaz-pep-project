pipeline {
  agent any
  
  stages {
    
    stage('Building and create .jar file'){
      steps {
        echo 'Building the jar file'
        
        // check maven version
        sh 'mvn -version'
        
        // builds and creates jar file
        sh 'mvn clean package'
      }
    }
    
    stage('Creating docker image') {
      steps {
        //removes all extra docker image
        sh 'sudo docker image prune -f'
        
        // build docker image
        sh 'sudo docker build -t ItsLaz/api:latest'
      }
    }
  }
}
