# Plan for Migrating AI Calendar to Gluon Mobile

This document outlines the plan to convert the existing Groovy-based AI Calendar desktop application into a mobile application for both iOS and Android using Gluon Mobile.

## 1. Project Setup and Dependencies

- **Modify `build.gradle`**: Update the Gradle build script to include the Gluon Mobile dependencies. This will involve adding the `gluonfx-plugin` and the necessary dependencies for Gluon Mobile components, such as `glisten`.
- **Update `settings.gradle`**: Ensure the project is configured correctly for Gradle.
- **Create `src/main/java` directory**: Gluon Mobile works well with Java, so we'll create a standard Java source directory.
- **Create `src/main/resources` directory**: This will be used for mobile-specific resources like FXML files, CSS, and images.

## 2. UI Rewrite with Gluon Mobile

The existing JavaFX UI is not suitable for mobile. We will create a new, mobile-first UI using Gluon Mobile's Glisten components.

- **Main View (`MainView.java`)**: Create a main view with a `BottomNavigation` component to switch between the primary sections of the app: Calendar, Chat, and Timeline.
- **Calendar View (`CalendarView.java`)**: Re-implement the monthly calendar grid using mobile-friendly controls. We can use a combination of `GridPane` and `Button` or a third-party calendar component if available.
- **Chat View (`ChatView.java`)**: Create a new chat interface using a `ListView` or a similar component to display chat messages. The input field and send button will be adapted for a mobile layout.
- **Timeline View (`TimelineView.java`)**: Re-implement the daily timeline view. This will likely involve a `ScrollView` with custom-drawn event blocks, similar to the desktop version but optimized for touch interaction.
- **Styling**: Create a new CSS file (`styles.css`) in `src/main/resources` to style the mobile UI components, ensuring a clean and modern look and feel.

## 3. View-Service Integration

We will connect the new mobile UI views to the existing backend services.

- **Connect to `CalendarService`**: The new UI views will interact with the existing `CalendarService` to manage events (create, read, update, delete).
- **Connect to `AIService`**: The new Chat view will use the existing `AIService` to send user queries to the AI and process the responses.

## 4. Platform-Specific Configuration

- **Android**: Configure the `build.gradle` file for Android builds, including the application ID, version codes, and any necessary permissions in the `AndroidManifest.xml`.
- **iOS**: Configure the `build.gradle` file for iOS builds, including the bundle ID and any necessary entitlements in the `Info.plist` file.

## 5. Build and Deployment

- **Gradle Tasks**: We will use the Gluon Mobile Gradle tasks to build and run the application on mobile devices and simulators.
  - `gradlew nativeRun`: Run the app on a connected device or simulator.
  - `gradlew nativeBuild`: Build the native app package (APK for Android, IPA for iOS).

By following this plan, we can efficiently migrate the AI Calendar application to mobile while reusing the core business logic, saving significant development time.
