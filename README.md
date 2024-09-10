# ClinicMessenger

## Overview

**ClinicMessenger** is a Java application developed as an advanced programming project. It demonstrates socket programming, multithreading, and GUI development, showcasing client-server communication and concurrent processing in Java.

## Features

- **Socket Programming**: Implements client-server communication over TCP.
- **Multithreading**: Handles multiple client connections simultaneously using threads.
- **GUI Application**: Provides a graphical user interface for user interaction.

## Dependencies

This project requires the [Gson library](https://github.com/google/gson) to handle JSON serialization and deserialization. 

- **Gson Version**: 2.2.2

**Note**: The Gson library is included manually in the project. Maven or other dependency management tools were not used at the time of development.

## Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/YaraMSN/ClinicMessenger.git
   ```

2. **Navigate to the Project Directory**

   ```bash
   cd ClinicMessenger
   ```

3. **Add Gson Library**

   Download the [Gson 2.2.2 JAR](https://search.maven.org/artifact/com.google.code.gson/gson/2.2.2/jar) and place it in the `lib` directory of the project (create this directory if it does not exist).

4. **Compile the Project**

   Use the following command to compile the Java files, ensuring that the Gson library is included in the classpath:

   ```bash
   javac -cp "lib/gson-2.2.2.jar" src/*.java
   ```

5. **Run the Application**

   Execute the compiled Java class, again specifying the Gson library in the classpath:

   ```bash
   java -cp "lib/gson-2.2.2.jar:src" MainClass
   ```

   Replace `MainClass` with the name of your main class.

## Usage

1. **Start the Server**

   Run the server component of the application first. It will listen for incoming client connections.

2. **Start the Client**

   Run the client component to connect to the server and interact with the application.

## Notes

- The roles of "doctor" and "patient" are placeholders and do not influence the core functionality of the program.
