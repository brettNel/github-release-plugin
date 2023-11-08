package org.jenkinsci.plugins.github.release.CreateReleaseStepTests

node {
    writeFile file: 'test.md', text: 'This is a test message.'
    createGitHubRelease(
            credentialId: 'a1234',
            githubServer: '%s',
            repository: 'jcustenborder/xjc-kafka-connect-plugin',
            tag: 'v1.2.3.4',
            commitish: '17b5676aaab28e334c0a9befc86e7615a7539c32',
            bodyFile: 'test.md',
            draft: true
    )
}