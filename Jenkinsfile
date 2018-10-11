#!groovy
node() {
    // 请完善信息 API自动测试（http://arp.test.innertoon.com/获得，或者找相关测试人员）
    def AUTOAPITESTBASE = "http://172.28.19.119:8085/Run.php?url=http://172.28.19.119:8082/v1/Createjsonext/createJsonByid&id="
    def T100PID = "202" //T100所对应项目在API自动测试系统中的ID
    def T200PID = "202" //T200所对应项目在API自动测试系统中的ID
    // war的Pom文件所在相对路径
    def POMPATH = "ui"

    // 部署环境（dev:t100 test:t200）
    def DEPLOYENV = ""
    def AUTOAPITEST = ""
    def mvnHome

    stage('Init&Checkout') {
        deleteDir()
        checkout scm

        def branchName = env.BRANCH_NAME
        switch (branchName) {
        case ['master', 'devlop', 'dev']:
            DEPLOYENV = "dev"
            AUTOAPITEST = "${AUTOAPITESTBASE}${T100PID}"
            break;
        case ['test', 'release']:
            DEPLOYENV = "test"
            AUTOAPITEST = "${AUTOAPITESTBASE}${T200PID}"
            break;
        default:
            DEPLOYENV = "dev"
            AUTOAPITEST = "${AUTOAPITESTBASE}${T100PID}"
        }

        mvnHome = tool 'Maven3'
    }

    stage('F-Build'){
        sh '''cd ui/src/main/webapp/lz_traffic/
        cnpm install
        cnpm run build
        rm -rf `ls | grep -v \'dist\'`'''
    }

    stage('B-Build') {
        sh "${mvnHome}/bin/mvn clean"
        sh "${mvnHome}/bin/mvn deploy -Dmaven.test.skip=true"

        //archiveArtifacts artifacts: "${POMPATH}/target/*.war", fingerprint: true
    }

    stage('Sonar') {
        withSonarQubeEnv('sonarServer') {
            sh "${mvnHome}/bin/mvn sonar:sonar"
        }

        timeout(10) {
            def qg = waitForQualityGate()
            if (qg.status != 'OK') {
                error "Pipeline aborted due to quality gate failure: ${qg.status}"
            }else{
                echo '恭喜你通过了代码的质量检查！'
            }
        }
    }

    stage('deploy') {
        sh "python /home/jenkins/script/JenkinsGit.py ${DEPLOYENV} ${POMPATH}"
    }

    //若没有API自动自动化测试，请注释本步
    stage('API-Test') {
        def r = httpRequest "${AUTOAPITEST}"
        writeFile encoding: 'UTF-8', file: 'temp', text: r.content
        junit 'temp'
    }
}