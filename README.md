# ferris-tweial

A Twitter-to-email client

# Production Deployment Procedures

1. Commit all files.
1. Update pom.xml, set production version
1. Commit all POM files.
1. Create TAG for the next version.
1. Update pom.xml, set next SNAPSHOT version
1. Commit all POM files.
1. Push all (including TAG) to GitHub
1. Checkout the TAG
1. Run action "production-artifacts"
1. Store zip,tar.gz on desktop for uploading to GitHub
1. Run action "production-site-deploy"
1. Close sprint, set production version, get report
1. Create GitHub release
1. Upload zip,tar.gz to GitHub release
