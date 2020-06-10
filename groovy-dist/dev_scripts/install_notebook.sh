#!/bin/bash
(cd ../; conda env create -n $1 -f configuration.yml)
source ~/anaconda3/etc/profile.d/conda.sh
conda activate $1
(cd ../; pip install -r requirements.txt --verbose)
beakerx_kernel_groovy install
echo To activate this environment, use:
echo      
echo      conda activate $1
echo      
