#!/bin/bash

# all projects in one workspace
# usage:
# ./install_kernels_notebook.sh beakerx_conda_env_name
#  conda activate beakerx_conda_env_name

(cd ../; conda env create -n $1 -f configuration.yml)
source ~/anaconda3/etc/profile.d/conda.sh
conda activate $1

(cd ../; pip install -r requirements.txt --verbose)
(cd ../../../beakerx_kernel_java/java-dist; pip install -r requirements.txt --verbose)
(cd ../../../beakerx_kernel_scala/scala-dist; pip install -r requirements.txt --verbose)
(cd ../../../beakerx_kernel_kotlin/kotlin-dist; pip install -r requirements.txt --verbose)
(cd ../../../beakerx_kernel_sql/sql-dist; pip install -r requirements.txt --verbose)
(cd ../../../beakerx_kernel_clojure/clojure-dist; pip install -r requirements.txt --verbose)
(cd ../../../beakerx_kernel_autotranslation; pip install -r requirements.txt --verbose)

beakerx_kernel_groovy install
beakerx_kernel_java install
beakerx_kernel_scala install
beakerx_kernel_kotlin install
beakerx_kernel_sql install
beakerx_kernel_clojure install
beakerx_kernel_autotranslation install

echo To activate this environment, use:
echo
echo      conda activate $1
echo      
