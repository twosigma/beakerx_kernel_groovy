#!/bin/bash

# all projects from local conda package
# usage:
# build all conda packages locally
# ./install_conda_packages_notebook.sh beakerx_conda_env_name
#  conda activate beakerx_conda_env_name

conda create -y -n $1 python=3.7 pip
source ~/anaconda3/etc/profile.d/conda.sh
conda activate $1
conda install -y --use-local beakerx_kernel_groovy
conda install -y --use-local beakerx_kernel_java
conda install -y --use-local beakerx_kernel_scala
conda install -y --use-local beakerx_kernel_sql
conda install -y --use-local beakerx_kernel_clojure
conda install -y --use-local beakerx_kernel_kotlin
conda install -y --use-local beakerx_kernel_autotranslation

echo To activate this environment, use:
echo
echo      conda activate $1
echo      
