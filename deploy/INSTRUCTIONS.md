# Instructions to run the pipeline

## Considerations

* This project uses the Apache Beam Direct Runner to run locally in the machine.
* No cloud resources are used.
* The following pre-requisites are needed wherever the code will run:
  * Java version `openjdk 11.0.11 2021-04-20` is recommended.
  * Apache Maven version `Apache Maven 3.8.5` is recommended.
  * All the needed dependencies are included in the `pom.xml` file in the `src` folder.

## How to run it

### Powershell

In the root folder of the project, there is a script called `run_pipeline.ps1`. This pipeline receives the input dataset as parameter and starts the pipeline. For reference, see the example below:

```
.\run_pipeline.ps1 data/season-0910_json.json
```

This will run the pipeline using the file called `season-0910_json.json` in the folder `data`.

### Bash

In the root folder of the project, there is a script called `run_pipeline.ch`. This pipeline receives the input dataset as parameter and starts the pipeline. For reference, see the example below:

```
.\run_pipeline.sh data/season-0910_json.json
```

This will run the pipeline using the file called `season-0910_json.json` in the folder `data`.


## Troubleshooting

In case of any error during the execution, check the following:

* Java is installed.
* Maven is installed.
* The input file exists.
* The script to execute the pipeline has _execution_ permission.
