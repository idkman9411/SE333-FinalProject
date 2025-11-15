from fastmcp import FastMCP
import os
import xml.etree.ElementTree as ET 

mcp = FastMCP("Demo 🚀")
def main():
    print("Hello from finalproject!")

@mcp.tool
def add(a: int, b: int) -> int:
    """Add two numbers"""
    return a + b

#Phase 2 
@mcp.tool
def getCodeBase()-> str:
    """Gets the path code base """
    print("In getCodeBase tool")
    currDir = os.getcwd()
    path = os.path.join(currDir,"codebase","src","main","java")
    if os.path.isdir(path):
        codeBasePath = path
    return codeBasePath


@mcp.tool
def getPomXml()-> str:
    """Gets the path to pom.xml """
    currDir = os.getcwd()
    path = os.path.join(currDir,"codebase","pom.xml")
    if os.path.isfile(path):
        pomFile =path
    return pomFile

@mcp.tool   
def configJacoco()-> str:
    """Gets the path to jacoco report """
    currDir = os.getcwd()
    path = os.path.join(currDir,"codebase","target","site","jacoco","jacoco.xml")
    if os.path.isfile(path):
        report = path
    return report
    

@mcp.tool
def getTotalCoverage(report: str)-> float:
    """Parses the jacoco report and returns the total coverage percentage"""
    with open(report, 'r') as file:
        tree = ET.parse(file)
        root = tree.getroot()
        counter = root.find(".//counter[@type='INSTRUCTION']")
        if counter is not None:
            covered = int(counter.get('covered'))
            missed = int(counter.get('missed'))
            total = covered + missed
            coverage_percentage = (covered / total) * 100 if total > 0 else 0
            return coverage_percentage
        

@mcp.tool   
def getMissingCoverage(report:str) -> dict[str,list[str]]:
    """Parses the jacoco report and returns the classes and methods that are missing coverage"""
    toBeCovered = {}
    with open(report, 'r') as file:
        tree = ET.parse(file)
        root = tree.getroot()
        for codeClass in root.find(".//class"):
            if codeClass is not None:
                className = codeClass.get('name')
                for method in codeClass.findall('method'):
                    missedMethods = []
                    methodName = method.get('name')
                    counter = method.find(".//counter[@type='INSTRUCTION']")
                    counterNum = int(counter.get('missed'))
                    if counterNum > 0:
                        missedMethods.append(methodName)
                toBeCovered[className] = missedMethods
    return toBeCovered
        
   # return {
    #    "coverageTool": configJacoco(),
    #    "threshold": 80
    #}


@mcp.tool
def getTestCases() -> str:
    """Gets the path to test cases in code base"""
    currDir = os.getcwd()
    path = os.path.join(currDir,"codebase","src","test","java")
    if os.path.isdir(path):
        testPath = path
    return testPath
    




#Phase 3

@mcp.tool
def git_status()-> str:
    """Gets the git status of the local repository"""
    status = os.popen("git status").read()
    return status


@mcp.tool
def git_add_all()-> str:
    """Stages all changes in the local repository"""
    with open(".gitignore","r") as file:
        gitignoreContent = file.read()
        rules  = ["*.class", "*.jar", "target/", ".idea/", "*.iml", ".vscode/"]
        with open(".gitignore","a") as f:
            for rule in rules:
                if rule not in gitignoreContent:
                    f.write(rule + "\n")
    
    os.system("git add --all")
    addedFiles = os.popen("git status").read()
    return addedFiles


@mcp.tool
def git_commit(message: str):
    """Commits staged changes with a given commit message"""
    os.system(f'git commit -m "{message}"')

@mcp.tool   
def git_push(remote ="origin"):
    """Pushes committed changes to the specified remote repository"""
    currBranch = os.popen("git branch --show-current").read()
    if currBranch.strip() == "main":
        os.system(f"git branch -u {remote}/coverageTesting")
    else:
        os.system(f"git push {remote} coverageTesting")

@mcp.tool
def git_pull_request(title:str,body:str,base="main") -> str:
    """Creates a pull request to the specified base branch"""
    #Create a pull request on GitHub.Upon success, the URL of the created pull request will be printed.
    url = os.popen(f'gh pr create --base {base} --title "{title}" --body "{body}"').read()
    return url


if __name__ == "__main__":
    #print("Registered tools:", mcp.tool.keys())
    print("Starting MCP...")
    (mcp.run(transport="sse"))

    # run it with `uv run python main.py`
