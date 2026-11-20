<!--
    Copyright 2020 TWO SIGMA OPEN SOURCE, LLC

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

           http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
-->

<img width="900" alt="banner" src="https://user-images.githubusercontent.com/963093/34594978-31d70312-f1a2-11e7-861c-705a9e932c3c.png">

# BeakerX: Beaker extensions for Jupyter - Groovy kernel

BeakerX is a collection of JVM kernels and interactive widgets for
plotting, tables, autotranslation, and other extensions to Jupyter
Notebook.

This repository contains BeakerX kernel for Groovy.

Please check the main repositoy for further informations:
[beakerx](https://github.com/twosigma/beakerx)


### Build and Install (Java 17+)

```
./gradlew build
```

The kernel uses Apache Groovy 6.0.0 and targets Java 25 by default. Use
`-PjavaVersion=17` for the Java 17 compatibility build.

### Browser Acceptance Test

After installing the kernel into the active Python environment, run the
Serenity browser test from the Airlock desktop environment:

```
DISPLAY=:99 ./gradlew :groovy:serenityTest
```

The Java Serenity test starts JupyterLab, opens a Groovy notebook in Chromium,
enters `6 * 7`, executes the cell, and asserts the visible result is `42`.

### Kernel Install in Conda (linux and mac)

```
cd ./groovy-dist
conda env create -n beakerx -f configuration.yml
conda activate beakerx # For conda versions prior to 4.6, run: source activate beakerx
(pip install -r requirements.txt --verbose)
beakerx_kernel_groovy install
cd ..
```
