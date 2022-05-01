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
		     properties([pipelineTriggers([pollSCM('H */1 * * *')])])
	       }
               checkout([$class: 'GitSCM', branches: [[name: '*/QA']], extensions: [], userRemoteConfigs: [[credentialsId: 'myGithub', url: 'https://github.com/Prasanna7396/JavaSpringApp.git']]]) 
	   }
        }
        stage('Selenium Test cases') {
            steps {
                sh 'mvn clean test -Dtest="TestSelenium" surefire-report:report-only'
            }
            post {
                success {
                   sh 'echo "Testing is completed!"'
                }
            }
        } 	
        stage('SonarQube Analysis'){
             steps {
               withSonarQubeEnv('sonarqube-8.9.2') { 
         	  sh "mvn sonar:sonar -Dsonar.projectKey=JavaWebAppQA"
            }
         }
        } 		
        stage('Docker Build and Image Push'){
             steps {
                  echo "Creating the docker image"
		  sh 'docker build -t "$DOCKERHUB_USER"/"$REGISTRY_NAME":"QA"-"$BUILD_NUMBER" .'
            }
            post {
                success {
                    echo "Pushing the docker image to docker hub"
		    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                    sh 'docker push "$DOCKERHUB_USER"/"$REGISTRY_NAME":"QA"-"$BUILD_NUMBER"'					
                }
		always {
		    sh 'docker logout'
		}
            }			
        }
         stage('Docker Deployment - QA') {
            steps{
		emailext body: '${FILE, path="target/site/surefire-report.html"}',
                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                mimeType: 'text/html',
                subject: "QA Test Report - Build No - ${env.BUILD_NUMBER} | Job: ${env.JOB_NAME}"
		    
                timeout(time:5, unit:'DAYS'){
                input message:'Approve QA Deployment?'
                }  
                sh 'docker run -d -p 8092:8080 --name java-qa-"$BUILD_NUMBER" "$DOCKERHUB_USER"/"$REGISTRY_NAME":"QA"-"$BUILD_NUMBER"'
            }
        }
    }
	post {
	      always {
		emailext body: "Deployment Status: ${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                subject: "QA Deployment -  Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
        }	
    }
}
