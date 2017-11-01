============
Contributors
============

.. image:: https://img.shields.io/github/contributors/jcustenborder/${model.artifactId}.svg

The following individual(s) have contributed to this project.

.. csv-table::
    :header: "Name","Role"

<#list model.developers as developer>
    "`${developer.name} <${developer.url}>`_","${developer.roles?join(", ")}"
</#list>