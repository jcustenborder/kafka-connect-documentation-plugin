<#if model.groupId??><#assign groupId=model.groupId><#else><#assign groupId=model.parent.groupId></#if>
=======
Install
=======

.. image:: https://img.shields.io/github/license/jcustenborder/${model.artifactId}.svg

.. image:: https://img.shields.io/github/release/jcustenborder/${model.artifactId}.svg
    :target: https://github.com/jcustenborder/${model.artifactId}/releases

.. image:: https://img.shields.io/maven-central/v/${groupId}/${model.artifactId}.svg
    :target: https://search.maven.org/#artifactdetails%7C${groupId}%7C${model.artifactId}%7C${model.version}%7Cjar

^^^^^^^^^^^^^^^^
RPM Installation
^^^^^^^^^^^^^^^^

Before starting the RPM installation you must configure the :ref:`yum_repository` first.

.. code-block:: bash

    sudo yum install ${model.artifactId}


^^^^^^^^^^^^^^^^
DEB Installation
^^^^^^^^^^^^^^^^

Before starting the RPM installation you must configure the :ref:`apt_repository` first.

.. code-block:: bash

    sudo apt-get install ${model.artifactId}


^^^^^^^^^^^^^^^^^^
Maven Installation
^^^^^^^^^^^^^^^^^^

.. code-block:: xml

    <dependency>
        <groupId>${groupId}</groupId>
        <artifactId>${model.artifactId}</artifactId>
        <version>${model.version}</version>
    </dependency>


