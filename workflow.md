1) Crear una Issue con el titulo que defina lo que se soluciona.
Ejemplo de titulo *Layout del login*

2) Pararse en master y hacer *git checkout -b numeroissue-nombreissue*.
Ejemplo *git checkout -b 1-layout-login*
(siempre con master actualizado)

3) Una vez finalizado el Issue en la branch correspondiente hacer un commit, luego hacer un rebase
con master (Si se encuentra con commits atrasados, tener siempre actualizado master).
Pararse en la branch del issue y hacer *git rebase -i master*
Subir solo un commit por issue. Para aplanar varios commits en uno solo leer
*rebase* en la docu de git.
Luego hacer *git push -f* en la branch de la issue

4) En la web de github realizar el pull-request poniendo en la descripcion
*fixes #1* el *1* es el numero de la issue. Eso va a hacer que al mergear esa branch se cierre dicha issue.

5) Todos los integrantes deben dar el visto bueno del pull-request para poder mergear con master.
