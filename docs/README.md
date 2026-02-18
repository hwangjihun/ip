# Hunnie User Guide

Hunnie is a desktop GUI chatbot for managing tasks.
You interact with Hunnie by typing commands into the app's input box and
reading replies in the chat panel.

![Product screenshot](UI.png)

- [Quick start](#quick-start)
- [Setting up in IntelliJ](#setting-up-in-intellij)
- [Running the application](#running-the-application)
- [Using the GUI](#using-the-gui)
- [Features](#features)
- [FAQ](#faq)
- [Known issues](#known-issues)
- [Command summary](#command-summary)

* * *

## Quick start

1. Ensure Java `17` or above is installed.
2. Open the project folder.
3. Launch Hunnie.
4. In the GUI, type a command into the input box at the bottom.
5. Press Enter (or click `Send`) to submit.
6. Read Hunnie's response in the chat area.

Try these commands:

- `todo revise tutorial`
- `deadline submit report /by 2026-03-01`
- `list`
- `bye`

* * *

## Setting up in IntelliJ

Prerequisites: JDK 17 or later, update IntelliJ to the most recent version.

1. Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first).
2. Open the project into IntelliJ:
   1. Click `Open`.
   2. Select the project directory, and click `OK`.
   3. If there are further prompts, accept the defaults.
3. Configure the project to use **JDK 17** (not other versions) as explained [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).
   In the same dialog, set **Project language level** to `SDK default`.
4. Locate `src/main/java/hunnie/gui/Launcher.java`, right-click it, and choose `Run Launcher.main()`.
   If setup is correct, the Hunnie chat window should appear.

* * *

## Running the application

### Using Gradle

```bash
# Build the project
./gradlew build

# Run the application
./gradlew run

# Create JAR file
./gradlew shadowJar
```

### Using JAR file

```bash
java -jar hunnie.jar
```

* * *

## Using the GUI

- Chat area:
  shows your messages and Hunnie's replies.
- Input box:
  where you type commands.
- `Send` button:
  submits the current command (same as pressing Enter).

After each successful command, the chat updates immediately.

* * *

## Features

Command format notes:

- Words in `<...>` are values you provide.
  - Example: `todo <description>` -> `todo read chapter 5`
- Task numbers are **1-based**.
  - Example: `mark 1` marks the first task in the list.
- For `deadline`, use separator ` /by `.
- For `event`, use separators ` /from ` and ` /to `.
- For multi-delete, separate numbers with comma + space.
  - Example: `delete 2, 4, 7`

### Adding a todo: `todo`

Adds a todo task.

Format: `todo <description>`

Examples:

- `todo revise CS2103 notes`
- `todo buy groceries`

Expected outcome:
Hunnie adds the task and shows the updated task count.

### Adding a deadline: `deadline`

Adds a deadline task with a due date.

Format: `deadline <description> /by <yyyy-MM-dd>`

Examples:

- `deadline submit report /by 2026-03-01`
- `deadline return library book /by 2026-02-25`

Expected outcome:
Hunnie adds a deadline task with a formatted due date.

### Adding an event: `event`

Adds an event task with start and end dates.

Format: `event <description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>`

Examples:

- `event sprint planning /from 2026-03-02 /to 2026-03-02`
- `event hackathon /from 2026-03-10 /to 2026-03-12`

Expected outcome:
Hunnie adds an event task with formatted date range.

### Listing all tasks: `list`

Shows all tasks currently in your list.

Format: `list`

Expected outcome:
Hunnie displays each task with index and completion status.

### Finding tasks: `find`

Finds tasks whose description contains the keyword.

Format: `find <keyword>`

Examples:

- `find report`
- `find meeting`

Expected outcome:
Hunnie shows only matching tasks.

### Marking a task as done: `mark`

Marks the specified task as done.

Format: `mark <task number>`

Examples:

- `mark 1`
- `mark 3`

Expected outcome:
The selected task status changes to done (`[X]`).

### Marking a task as not done: `unmark`

Marks the specified task as not done.

Format: `unmark <task number>`

Examples:

- `unmark 1`
- `unmark 3`

Expected outcome:
The selected task status changes to not done (`[ ]`).

### Deleting tasks: `delete`

Deletes one or multiple tasks.

Format: `delete <task number>`  
Format: `delete <task number>, <task number>, ...`

Examples:

- `delete 3`
- `delete 2, 4, 7`

Expected outcome:
Hunnie removes the selected task(s) and shows updated count.

### Exiting the app: `bye`

Exits Hunnie.

Format: `bye`

Expected outcome:
Hunnie shows a farewell message and closes shortly after.

### Saving data

Hunnie saves automatically after every modifying command.
No manual save command is needed.

### Data file location

Data is stored in:

`src/main/data/hunnie.txt`

If the file is missing, Hunnie starts with an empty task list.

* * *

## FAQ

**Q: How do I submit a command in the GUI?**  
A: Type in the input box and press Enter, or click `Send`.

**Q: Why did my command fail even though the words look right?**  
A: Some commands require exact separators such as ` /by `, ` /from `, ` /to `,
and `, `. Check [Features](#features).

**Q: What date format should I use?**  
A: Use `yyyy-MM-dd` (e.g., `2026-03-01`).

* * *

## Known issues

1. `deadline`, `event`, and multi-index `delete` are strict about separators.

* * *

## Command summary

Action | Format
--- | ---
Add todo | `todo <description>`
Add deadline | `deadline <description> /by <yyyy-MM-dd>`
Add event | `event <description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>`
List tasks | `list`
Find tasks | `find <keyword>`
Mark task | `mark <task number>`
Unmark task | `unmark <task number>`
Delete task(s) | `delete <task number>` or `delete <task number>, <task number>, ...`
Exit | `bye`
