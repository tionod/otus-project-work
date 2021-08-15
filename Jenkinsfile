pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
    }

    triggers {
        githubPush()
        cron('0 1 * * *')
    }

    parameters {
        string(name: 'GIT_URL', defaultValue: 'https://github.com/tionod/otus-project-work.git', description: 'The target git url')
        string(name: 'GIT_BRANCH', defaultValue: 'master', description: 'The target git branch')
        choice(name: 'BROWSER_NAME', choices: ['chrome', 'firefox', 'opera'], description: 'Pick the target browser in Selenoid')
        choice(name: 'BROWSER_VERSION', choices: ['89.0', '87.0', '74.0'], description: 'Pick the target browser version in Selenoid')
        choice(name: 'ENABLE_VNC', choices: [true, false], description: 'Select the enable/disable VNC in Selenoid')
        choice(name: 'ENABLE_VIDEO', choices: [true, false], description: 'Select the enable/disable video record in Selenoid')
        choice(name: 'ENABLE_LOGS', choices: [true, false], description: 'Select the enable/disable logging in Selenoid')
    }

    stages {
        stage('Pull from GitHub') {
            steps {
                git ([
                    url: "${params.GIT_URL}",
                    branch: "${params.GIT_BRANCH}"
                    ])
            }
        }
        stage('Run Tests') {
            steps {
                sh 'mvn clean'
                sh 'mvn test -Dbrowser="${params.BROWSER_NAME}" -DbrowserVersion="${params.BROWSER_VERSION}"
                 -DenableVNC=${params.ENABLE_VNC} -DenableVideo=${params.ENABLE_VIDEO} -DenableLogs=${params.ENABLE_LOGS}'
            }
        }
    }

    post {
         always {
            archiveArtifacts artifacts: '**/target/', fingerprint: true
            allure([
              includeProperties: false,
              jdk: '',
              properties: [],
              reportBuildPolicy: 'ALWAYS',
              results: [[path: 'target/allure-results']]
            ])
            println('allure report created')
          script {
            // Узнаем ветку репозитория
            def branch = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD\n').trim().tokenize().last()
            println("branch= " + branch)
            // Достаем информацию по тестам из junit репорта
            def summary = junit testResults: '**/target/surefire-reports/*.xml'
            println("summary generated")
            // Текст оповещения
            def message = "${currentBuild.currentResult}: Job ${env.JOB_NAME}, build ${env.BUILD_NUMBER}, branch ${branch}\n" +
            "Test Summary - ${summary.totalCount}, Failures: ${summary.failCount}, Skipped: ${summary.skipCount}, Passed: ${summary.passCount}\n" +
            "More info at: ${env.BUILD_URL}"
            println("message= " + message)
          }
         }
         success {
             echo 'SUCCESS RESULT'
         }
         failure {
            echo 'FAILURE RESULT'
             emailext body: """<p>Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                 <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
                 mimeType: 'text/html',
                 subject: "[Jenkins] " + currentBuild.fullDisplayName,
                 to: "testmailbox.otus@gmail.com"
         }
         unstable {
             echo 'UNSTABLE RESULT'
         }
     }
}