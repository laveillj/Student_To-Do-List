<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/mobile_navigation"
    app:startDestination="@+id/navigation_tasks">

    <fragment
        android:id="@+id/navigation_tasks"
        android:name="com.example.student_to_do_list.ui.tasks.TasksFragment"
        android:label="@string/title_tasks"
        tools:layout="@layout/fragment_tasks" />

    <fragment
        android:id="@+id/navigation_projects"
        android:name="com.example.student_to_do_list.ui.projects.ProjectsFragment"
        android:label="@string/title_projects"
        tools:layout="@layout/fragment_projects" />

    <fragment
        android:id="@+id/navigation_settings"
        android:name="com.example.student_to_do_list.ui.settings.SettingsFragment"
        android:label="@string/title_credit"
        tools:layout="@layout/fragment_settings" />
</navigation>