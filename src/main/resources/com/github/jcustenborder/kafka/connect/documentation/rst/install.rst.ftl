=======
Install
=======

.. image:: https://img.shields.io/github/release/jcustenborder/${model.artifactId}.svg
    :target: https://github.com/jcustenborder/${model.artifactId}/releases

.. image:: https://img.shields.io/maven-central/v/${model.groupId}/${model.artifactId}.svg
    :target: https://search.maven.org/#artifactdetails%7C${model.groupId}%7C${model.artifactId}%7C${model.version}%7Cjar


^^^^^^^^^^^^^^^^
RPM Installation
^^^^^^^^^^^^^^^^

.. code-block:: bash

    sudo yum install ${model.artifactId}

^^^^^^^^^^^^^^^^
DEB Installation
^^^^^^^^^^^^^^^^

.. code-block:: bash

    sudo apt-get install ${model.artifactId}

^^^^^^^^^^^^^^^^^^
Maven Installation
^^^^^^^^^^^^^^^^^^

.. code-block:: xml

    <dependency>
        <groupId>${model.groupId}</groupId>
        <artifactId>${model.artifactId}</artifactId>
        <version>${model.version}</version>
    </dependency>
