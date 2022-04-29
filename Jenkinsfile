pipeline {
    agent any
    environment {
        DOCKERHUB_CREDENTIALS=credentials('dockerHub')
	REGISTRY_NAME="simple-java-app"
        DOCKERHUB_USER="prasanna7396"
    }
    stages {
        stage('GetCode') { 
            steps {
		script{
		     properties([pipelineTriggers([pollSCM('H/1 * * * *')])])
		} 
                git branch: 'Dev', credentialsId: 'githubToken', url: 'https://github.com/Prasanna7396/JavaSpringApp.git'
	  }
        } 	
        stage('Build and Artifact archival') {
            steps {
                sh 'mvn clean package'
            }
            post {
                success {
                    echo "Now Archiving the Artifacts...."
                    archiveArtifacts artifacts: '**/*.war'
                }
            }
        }
        stage('Junit Test report generation') {
            steps {
                sh 'mvn surefire-report:report-only'
            }
        }
        stage('SonarQube Analysis'){
            steps {
               withSonarQubeEnv('sonarqube-8.9.2') { 
		sh "mvn sonar:sonar -Dsonar.projectKey=JavaWebAppDev"
            }
          }
        } 		
        stage('Docker Build and Image Push'){
             steps {
                  echo "Creating the docker image"
		  sh 'docker build -t "$DOCKERHUB_USER"/"$REGISTRY_NAME":"Dev"-"$BUILD_NUMBER" .'
            }
            post {
                success {
                    echo "Pushing the docker image to docker hub"
		    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                    sh 'docker push "$DOCKERHUB_USER"/"$REGISTRY_NAME":"Dev"-"$BUILD_NUMBER"'					
                }
		always {
		    sh 'docker logout'
		}
            }			
        }
         stage('Docker Deployment - DEV') {
            steps{
                sh 'docker run -d -p 8091:8080 --name java-dev-"$BUILD_NUMBER" "$DOCKERHUB_USER"/"$REGISTRY_NAME":"Dev"-"$BUILD_NUMBER"'
            }
        }
    }
	post {
	      always {
		emailext body: "Deployment Status: ${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                subject: "Dev Deployment -  Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
        }	
    }	
}
