import re

regex_dependency_graph = '(\().*(\S+?(?:jpe?g|png|gif))'
replace_dependency_graph = '(docs/images/project-dependency-graph.png'

with open ('./README.md', 'r+' ) as file:
    content = file.read()
    file.seek(0)

    content_new = re.sub(regex_dependency_graph, replace_dependency_graph, content)

    file.write(content_new)
    file.truncate()