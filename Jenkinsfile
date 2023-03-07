pipeline {
  agent any
  
  stages {
    
    stage('Building and create .jar file'){
      steps {
        echo 'Building the jar file'
        
        sh 'mvn -version'
        
        // builds and creates jar file
        sh 'mvn clean package'
      }
    }
  }
}
