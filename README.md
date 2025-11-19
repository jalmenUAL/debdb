# Vaadin Web Tool for SPARQL DBpedia Queries Debugger

**University of Almería, 2025**

This is the **Vaadin-based Web implementation** of the debugger for SPARQL DBpedia queries. The web application provides a user-friendly interface for debugging queries, visualizing results, and testing expected and unexpected answers without directly interacting with the Prolog console.

More information and source code can be found at: [https://github.com/jalmenUAL/debdb-ual](https://github.com/jalmenUAL/debdb-ual)

---

## Prerequisites

Before running the web tool, ensure you have the following installed:

1. **SWI-Prolog**
   - Download and install from [https://www.swi-prolog.org](https://www.swi-prolog.org)

2. **Java Development Kit (JDK)**
   - Required for running Vaadin applications

3. **Set Environment Variables**
   - Configure Java-Prolog integration using [https://jpl7.org/DeploymentWindows](https://jpl7.org/DeploymentWindows)

---

## Installation & Setup

1. **Download Vaadin Project**
   - Clone the repository:

```bash
git clone https://github.com/jalmenUAL/debdb-ual.git
```

2. **Open Project**
   - Open the Vaadin project in your preferred IDE (IntelliJ IDEA, Eclipse, or VS Code)

3. **Configure Prolog Path**
   - Ensure the SWI-Prolog installation path is correctly set in your environment variables

---

## Running the Application

1. **Start the Vaadin Application**
   - In your IDE, run the main class (usually `MainView` or similar) or use Maven:

```bash
mvn spring-boot:run
```

2. **Access the Web Tool**
   - Open your browser and go to [http://localhost:8080](http://localhost:8080)

3. **Using the Debugger**
   - Enter your SPARQL query in the input area
   - Define sets of expected and unexpected answers
   - Submit the query for debugging
   - View the results and suggested weak rules directly in the interface

---

## Features

- User-friendly interface for SPARQL debugging
- Direct integration with SWI-Prolog for query evaluation
- Visualization of expected vs unexpected answers
- Step-by-step debugging support

---

## References

- [Vaadin Framework](https://vaadin.com)
- [DBpedia](https://wiki.dbpedia.org/)
- [SWI-Prolog](https://www.swi-prolog.org/)

---

## License

This project is released under the MIT License.

