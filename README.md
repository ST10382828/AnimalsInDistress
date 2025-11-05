# Animals In Distress - Android Application

## Project Overview

This is an Android application for the Animals In Distress organization, built with Kotlin and following modern Android development best practices.

## Code Quality

### Code Quality URL

**SonarQube Project Link**: https://sonarcloud.io/project/overview?id=ST10382828_AnimalsInDistress

**Note**: Please ensure that all markers/instructors have access to the SonarQube project. To grant access:
1. Go to your SonarQube project settings
2. Navigate to "Permissions" or "Access Control"
3. Add the markers' email addresses or user accounts
4. Grant appropriate permissions (at minimum: "Browse" and "See Code" permissions)

### Code Quality Metrics

- **Code Coverage**: Available in SonarQube dashboard
- **Code Duplication**: Monitored and reported
- **Code Smells**: Tracked and resolved
- **Security Vulnerabilities**: Scanned and addressed
- **Maintainability Rating**: Available in SonarQube

## Security

### Security Report URL

**Snyk Project Link**: https://app.snyk.io/org/st10382828/project/c4523e7d-e6d6-465f-b786-c46d2baaab86

**Note**: Snyk projects are private by default. To grant markers access, you have two options:

**Option 1: Invite Collaborators (Recommended)**
1. Go to https://app.snyk.io
2. Click your organization name (top right) → **"Settings"**
3. Go to **"Collaborators"** section
4. Click **"Invite Collaborators"**
5. Enter markers' email addresses
6. Set role to **"Viewer"** (read-only access)
7. Send invitations

**Option 2: Share Security Report**
- A comprehensive security report has been generated:
  - **PDF Version**: `SNYK_SECURITY_REPORT.pdf` (ready to share)
  - **Markdown Version**: `SNYK_SECURITY_REPORT.md` (source file)
- This report includes all vulnerabilities, severity levels, and recommendations
- You can share the PDF file with markers

### Security Scan Results

- **Total Issues Found**: 17 vulnerabilities (mostly in dependencies)
- **High Severity**: 10 issues
- **Medium Severity**: 5 issues
- **Low Severity**: 2 issues
- **Status**: Monitored and tracked in Snyk dashboard

## Project Structure

```
AnimalsInDistress/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/student/projects/animalsindistress/
│   │   │   │   ├── data/          # Data models and repositories
│   │   │   │   ├── ui/            # UI fragments and activities
│   │   │   │   └── util/          # Utility classes
│   │   │   ├── res/               # Resources (layouts, drawables, values)
│   │   │   └── AndroidManifest.xml
│   │   └── test/                  # Unit tests
│   └── build.gradle.kts
├── build.gradle.kts
└── settings.gradle.kts
```

## Features

- **Success Stories**: Instagram/TikTok-style vertical feed with before/after transformations
- **Gallery**: Image gallery with categories and full-view functionality
- **Donations**: In-app donation system with impact tracking
- **Admin Panel**: Content management for stories, gallery, and medical aid requests
- **Authentication**: User login and registration
- **Offline Support**: Local caching for offline functionality

## Technology Stack

- **Language**: Kotlin
- **Minimum SDK**: 35
- **Target SDK**: 36
- **Build System**: Gradle (Kotlin DSL)
- **Architecture**: MVVM-ready with Repository pattern
- **Image Loading**: Coil
- **Navigation**: Android Navigation Component
- **Backend**: Firebase (Firestore, Storage, Authentication)

## Building the Project

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Build and run on an Android device or emulator (API 35+)

## Documentation

- `IMPLEMENTATION_SUMMARY.md` - Feature implementation details
- `SUCCESS_STORIES_CODE_REVIEW.md` - Code review and improvements
- `DONATION_IMPACT_TRACKER.md` - Donation feature documentation
- `GALLERY_PERFORMANCE_GUIDE.md` - Gallery optimization guide

## License

This project is for educational purposes.

---

**Important**: Please update the SonarQube project URL above and ensure all markers have access to the code quality dashboard.

