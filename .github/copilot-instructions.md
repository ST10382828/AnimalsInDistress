# Animals In Distress - AI Coding Agent Instructions

## Project Overview
**Animals In Distress** is a native Android app for an animal welfare organization. Built using Kotlin with Android Fragments and XML layouts, the app provides information about services, donation options, news, galleries, and volunteer opportunities.

**Package**: `student.projects.animalsindistress`  
**Min SDK**: 35 (Android 15+)  
**Target SDK**: 36

## Architecture & Navigation

### Fragment-Based Architecture
- **Single Activity Pattern**: `MainActivity` hosts all fragments via `NavHostFragment`
- **Navigation**: Android Navigation Component with graph defined in `app/src/main/res/navigation/nav_graph.xml`
- **29 Fragments** in `app/src/main/java/student/projects/animalsindistress/ui/fragments/`
  - Top-level destinations: `homeFragment`, `aboutFragment`, `galleryFragment`, `donateFragment`, `moreFragment`
  - Detail fragments like `NewsDetailFragment` accept navigation arguments (e.g., `slug: String`)

### Navigation UI Pattern
- **Triple Navigation System**:
  1. **Bottom Navigation Bar**: 5 main sections (Home, About, Gallery, Donate, More)
  2. **Dynamic Drawer Menu**: Auto-updates with "Recent Pages" (last 5 visited), Quick Links, and Programs sections
  3. **Top Toolbar**: Contextual back button (hidden on top-level destinations), logo/title toggle, menu button

Key implementation in `MainActivity.kt`:
- `topLevelDestinations` set defines when to hide back button
- `recentPages` list dynamically builds drawer menu via `updateDrawerMenu()`
- Navigation listener updates toolbar state on destination changes

### Layout System
- **XML-based layouts** in `app/src/main/res/layout/`
- **Include patterns**: `include_header.xml`, `include_footer.xml`, `include_bottom_nav.xml`
- **Fragment constructor pattern**: `Fragment(R.layout.fragment_name)` - no need to override `onCreateView()`

## Key Patterns & Conventions

### Fragment Implementation
```kotlin
class ExampleFragment : Fragment(R.layout.fragment_example) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // All setup happens here
        view.findViewById<Button>(R.id.my_button)?.setOnClickListener {
            findNavController().navigate(R.id.targetFragment)
        }
    }
}
```

### Navigation with Arguments
Use Safe Args for type-safe navigation arguments:
```kotlin
// In nav_graph.xml
<fragment android:id="@+id/newsDetailFragment" ...>
    <argument android:name="slug" app:argType="string" />
</fragment>

// In fragment
private val args: NewsDetailFragmentArgs by navArgs()
val slug = args.slug
```

### Image Loading
Use **Coil** for image loading:
```kotlin
// For Compose-like usage
imageView.load("https://example.com/image.jpg")

// Dependency: io.coil-kt:coil:2.6.0 (for XML views)
```

### External Links & Intents
```kotlin
// Browser
val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
startActivity(intent)

// Phone dialer
Intent(Intent.ACTION_DIAL, Uri.parse("tel:0113231920"))

// Email
Intent(Intent.ACTION_SENDTO).apply { 
    data = Uri.parse("mailto:admin@animalsindistress.org.za") 
}
```

## Build & Configuration

### Dependencies Management
Version catalog in `gradle/libs.versions.toml`:
- Material 3 Components
- AndroidX Navigation Compose (declared but not actively used)
- Coil for image loading
- **Hilt DI removed** (comments indicate build issues)

### Lint Configuration
Windows-specific lint settings in `app/build.gradle.kts`:
```kotlin
lint {
    disable += "PropertyEscape"  // Fixes local.properties issues
    checkReleaseBuilds = false   // Avoids file locking on Windows
}
```

### Build Commands (PowerShell)
```powershell
# Build debug APK
.\gradlew.bat assembleDebug

# Install on device
.\gradlew.bat installDebug

# Clean build
.\gradlew.bat clean build
```

## Theming & Styling

**Material 3 NoActionBar Theme**: `Theme.AnimalsInDistress` in `app/src/main/res/values/themes.xml`
- Custom color palette: `@color/primary`, `@color/secondary`, `@color/card`, etc.
- Bottom nav custom indicator: `App.NavigationBarActiveIndicator` style
- Color state lists for nav items: `@color/bottom_nav_icon_color`, `@color/nav_drawer_color`

## Data & Content Strategy

### Static Content Pattern
Content is hardcoded in fragments/layouts - no backend integration:
- Gallery categories with image URL arrays (memory, animals, volunteers, diamonds, horses, events)
- News articles stored as `Map<String, Triple<String, String, List<String>>>` in `NewsDetailFragment`
- Donation amounts link to PayFast URLs with query parameters

### Future Integration Notes
- `AndroidManifest.xml` requests `INTERNET` and `ACCESS_NETWORK_STATE` permissions
- Kotlinx Serialization dependency present but unused
- No data models or repositories currently defined

## Common Modifications

### Adding a New Fragment
1. Create layout XML in `res/layout/fragment_name.xml`
2. Create Kotlin class in `ui/fragments/NameFragment.kt`
3. Add to `nav_graph.xml` with unique `android:id`
4. Update drawer menu or bottom nav if needed

### Adding Navigation Destinations
Update drawer groups in `MainActivity.updateDrawerMenu()`:
- Recent Pages (auto-populated)
- Quick Links group (id=1)
- Programs group (id=2)

### Handling Form Submissions
Use `MaterialAlertDialogBuilder` for dialogs (see `ServicesFragment.kt`):
```kotlin
val dialogView = LayoutInflater.from(requireContext())
    .inflate(R.layout.dialog_request_medical_aid, null, false)
MaterialAlertDialogBuilder(requireContext()).setView(dialogView).create()
```

## Project Structure
```
app/src/main/
├── java/student/projects/animalsindistress/
│   ├── MainActivity.kt          # Single activity host
│   ├── ui/fragments/            # All 29 fragments
│   └── navigation/              # Empty (using XML nav graph)
├── res/
│   ├── layout/                  # XML layouts (activity + fragments)
│   ├── navigation/nav_graph.xml # Navigation graph
│   ├── menu/                    # Bottom nav & drawer menus
│   ├── values/themes.xml        # Material 3 theme
│   └── drawable/                # Icons (ic_* pattern)
└── AndroidManifest.xml
```

## Known Issues & Workarounds
- **AnimalsApp referenced in manifest doesn't exist** - likely legacy code, not causing runtime issues
- **Hilt removed due to build issues** - consider manual DI if needed
- **High minSdk (35)** limits device compatibility to Android 15+ only
