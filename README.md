# SimpleEditor

# Methods with high Cognitive Complexity (Critical)
High cognitive complexity in software can make it difficult to maintain, which can lead to increased likelihood of defects, increased cost and time to maintain, reduced ability to scale and adapt, and reduced usability and user satisfaction, ultimately negatively impacting software quality. Developers should strive for simplicity and maintainability in their designs to avoid these issues

# String literals should not be duplicated (Critical)
Duplicating string literals in software can negatively affect software quality by increasing maintenance effort, likelihood of errors, memory usage, and reducing maintainability. To avoid these issues, developers should use constants or variables to represent the string instead of duplicating it.

# Sections of code should not be commented out  (Major)
Commented-out code sections can negatively affect software quality by cluttering the code, becoming outdated, and introducing the risk of errors. Therefore, it is better to avoid using commented-out code and instead use version control systems to keep track of code changes and maintain a clean codebase.

# Null pointer exceptions (NPEs)  (Major)
can negatively impact software quality by causing the software to crash, produce incorrect or incomplete results, be difficult to debug, and harder to maintain. Defensive programming techniques, such as null checks and exception handling, can prevent NPEs and improve software quality, along with testing and code review processes to identify and address NPEs.

#Using a logger instead of System.out.println() can improve software quality  (Major)
by providing more flexibility, maintainability, performance, and debugging capabilities.
 
 # class variable  field should  not have public accsess (Minor):
 Avoiding public access to class variables or fields can have a positive impact on software quality by promoting encapsulation, modularity, and security.

# private methods that are never executed are dead code (Major): unnecessary, inoperative code that should be removed. Cleaning out dead code decreases the size of the maintained codebase, making it easier to understand the program and preventing bugs from being introduced.
Note that this rule does not take reflection into account, which means that issues will be raised on private methods that are only accessed using the reflection API.

# Filed names should comply with name convention (Minor):
Sharing some naming conventions is a key point to make it possible for a team to efficiently collaborate. This rule allows to check that field names match a provided regular expression.


# Multiple variable should not be declear on the same line (Minor):
  Declaring multiple variables on one line is difficult to read.

