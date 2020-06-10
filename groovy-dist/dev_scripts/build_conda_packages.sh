#!/bin/bash

# all projects in one workspace
# in the meta.yaml replace YOUR_LOCAL_PATH for all components
# usage:
# rm -rf ~/anaconda3/conda-bld/*
# ./build_conda_packages.sh

(cd ../; python setup.py sdist; conda-build ./conda_recipe)
(cd ../../../beakerx_kernel_java/java-dist; python setup.py sdist; conda-build ./conda_recipe)
(cd ../../../beakerx_kernel_scala/scala-dist; python setup.py sdist; conda-build ./conda_recipe)
(cd ../../../beakerx_kernel_kotlin/kotlin-dist; python setup.py sdist; conda-build ./conda_recipe)
(cd ../../../beakerx_kernel_sql/sql-dist; python setup.py sdist; conda-build ./conda_recipe)
(cd ../../../beakerx_kernel_clojure/clojure-dist; python setup.py sdist; conda-build ./conda_recipe)
(cd ../../../beakerx_kernel_autotranslation; python setup.py sdist; conda-build ./conda_recipe)
