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

**1.** Install and configure uv package manager

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

Paste your MCP server URL ( http://... )

Give it a name.

Press Enter.

Confirm the new tool is listed in your VS Code Chat view.


**5.** To Enable YOLO Mode

Press CTRL+SHIFT+P → search for Chat: Settings.

Enable Auto-Approve.

Make sure all tools are now highlighted for use.


**6.** To have maven run automatically

Press CTRL+ALT+P.

Open Auto-Approve Settings.

Add the entry: mvn test





<h2> MCP Tools Documentation </h2>

<h3> getCodeBase()</h3> 
<i>Return Type</i>: returns the file path of codebase/src/main/java as a <i>string</i>

Assumes that code base is in the same directory as <i>main.py</i>


<h3> getTestBase()</h3> 
<i>Return Type</i>: returns the file path of codebase/src/test/java as a <i>string</i>

Assumes that code base is in the same directory as <i>main.py</i>



<h3>configJacoco()</h3> 
<i>Return Type</i>: returns the file path of of jacoco.xml as <i>string</i>

Assumes that code base is in the same directory as *main.py* and that jacoco report is saved under target/site/jacoco



<h3>getTotalCoverage(report)</h3> 

<i>Parameter</i>: <b>report</b>: a string that represent the path to the jacoco.xml report

<i>Return Type</i>: returns the coverage percentage as a float



<h3>getMissingCoverage(report)</h3> 

<i>Parameter</i>: <b>report</b>: a string that represent the path to the jacoco.xml report

<i>Return Type</i>: returns a dictionary. <b>Keys</b> are the package class name. Ex. org/example/Vet. <b>Vaules</b> are list of <i>Str</i> method names in that class that have missing coverage.


<h3>getCheckstyle()</h3> 

<i>Return Type</i>: returns the file path of checkstyle-result.xml as string

Assumes that code base is in the same directory as *main.py* and that checkstyle report is saved under target


<h3>getMissingCoverage(report)</h3> 

<i>Parameter</i>: <b>report</b>: a string that represent the path to the checkstyle-result.xml report

<i>Return Type</i>: returns a dictionary. <b>Keys</b> are the full file paths of classes as strings. Ex. C:\Users\{You}\{Project}\src\main\java\org\Vet.java . <b>Vaules</b> are list of <i>Str</i> of the line where the violations occurred and the violation message.



<h3>git_status()</h3> 

<i>Return Type</i>: returns the result git status as a string



<h3>git_add_all()</h3> 

<i>Return Type</i>: returns the result of git status after adding files

Adds all of the relevant changed files to be staged.



<h3>git_commit(message)</h3> 

<i>Parameter</i>: <b>message</b>: a string that represent the commit message

Commits the staged files



<h3>git_push(remote="origin")</h3> 

<i>Parameter</i>: <b>remote</b>: a string that represents the repository you want to push to. Defaults to origin.

Pushes the commit to the remote 



<h3>git_pull_request(title,body,base="main")</h3> 

<i>Parameter</i>: <b>title</b>: a string that represent the title of the pull request

<b>body</b>: a string that represents the body of the pull request

<b>base</b>: a string that represents the branch to merge to. Defaults to main


<i>Return Type</i>: returns the url of the created pull request

Uses GitHub CLI to create a pull request using the terminal




<h2>Troubleshooting</h2>

The chat agent can be very finicky. You can change what the agent does by editing the description and prompt instruction in the .github\prompts\tester.prompt.md file.

When prompting the agent, give the name of the prompt file and specify it should follow the prompt instructions and the mcp tools. You might have to do this multiple times while it runs.
