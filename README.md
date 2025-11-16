Link To Repository: https://github.com/idkman9411/SE333-FinalProject


<h2>Installation and configuration guide</h2>

<h3>Prerequisites</h3>

VS Code with chat view

Git and a GitHub account

Python 3.10 

JDK 11+

Node.Js 18+

You'll need to have <a href=https://maven.apache.org> Maven 3.6+ </a> downloaded. 

You'll need to have <a href =https://github.com/cli/cli#installation> GitHubs CLI</a> installed in order to create pull request. To configure, type <a href =https://cli.github.com/manual/gh_auth_login >gh auth login</a> in the terminal to authenticate a GitHub Host


<h2>Configuration</h2>

**1.**Install and configure uv package manager
Set up Python virtual environment with MCP dependencies
Configure VS Code with MCP server integration

**2.** In the terminal type
uv init
uv venv
**On Windows:** .venv\Scripts\activate   **Others:** source .venv/bin/activate
uv add mcp[cli] httpx fastmcp


**3.** To start the server, type 
python main.py
*or*
uv run python main.py

**4.** To Connect the MCP Server to VSCode
Press CTRL+SHIFT+P → search for MCP: Add Server .
Paste your MCP server URL ( http://... ).SE333  Final Project on Software Agents 10%2
Give it a name.
Press Enter.
Confirm the new tool is listed in your VS Code Chat view.

**5.** To Enable YOLO Mode
Press CTRL+SHIFT+P → search for Chat: Settings.
Enable Auto-Approve.
Make sure all tools are now highlighted for use.
**6**To have maven run automatically
Press CTRL+ALT+P.
Open Auto-Approve Settings.
Add the entry: mvn test





<h2> MCP Tools Documentation </h2>

<h3> getCodeBase</h3> 
Return Type: returns the file path of codebase/src/main/java as a *string*

Assumes that code base is in the same directory as main.py


<h3> getTestBase</h3> 
Return Type: returns the file path of codebase/src/test/java as a *string*

Assumes that code base is in the same directory as *main.py*



<h3>configJacoco</h3> 
Return Type: returns the file path of of jacoco.xml as *string*

Assumes that code base is in the same directory as *main.py* and that jacoco report is saved under target/site/jacoco



<h3>getTotalCoverage(report)</h3> 

Parameter: **report**: a string that represent the path to the jacoco.xml report

Return Type: returns the coverage percentage as a float



<h3>getMissingCoverage(report)</h3> 

Parameter: **report**: a string that represent the path to the jacoco.xml report

Return Type: returns a dictionary. **Keys** are the package class name. Ex. org/example/Vet. **Vaules** are list of *Str* method names in that class that have missing coverage.


<h3>getCheckstyle()</h3> 

Return Type: returns the file path of checkstyle-result.xml as *string*

Assumes that code base is in the same directory as *main.py* and that checkstyle report is saved under target


<h3>getMissingCoverage(report)</h3> 

Parameter: **report**: a string that represent the path to the checkstyle-result.xml report

Return Type: returns a dictionary. **Keys** are the full file paths of classes as *strings*. Ex. C:\Users\{You}\{Project}\src\main\java\org\Vet.java . **Vaules** are list of *Str* of the line where the violations occurred and the violation message.



<h3>git_status()</h3> 

Return Type: returns the result git status as a string



<h3>git_add_all()</h3> 

Return Type: returns the result of git status after adding files

adds all of the relevant changed files to be staged.


<h3>git_commit(message)</h3> 

Parameter: **message**: a string that represent the commit message


<h3>git_push(remote="origin")</h3> 

Parameter: **remote**: a string that represents the repository you want to push to. Defaults to origin

<h3>git_pull_request(title,body,base="main")</h3> 

Parameter: **title**: a string that represent the title of the pull request
           **body**: a string that represents the body of the pull request
           **base**: a string that represents

Return Type: returns the url of the created pull request

Uses GitHub CLI to create a pull request using the terminal




<h2>Troubleshooting</h2>

The chat agent can be very finicky. You can change what the agent does by editing the description and prompt instruction in the .github\prompts\tester.prompt.md file.

When prompting the agent, give the name of the prompt file and specify it should follow the prompt instructions and the mcp tools. You might have to do this multiple times while it runs.
