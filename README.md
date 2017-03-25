# Strategy Game Stratego

by jualtmey and redcarwolf

## used technologies
|picture|name|used|
|---|---|---|
||[google guice](https://github.com/google/guice)|makes it modular using **dependency injection**|
||gradle|buildtool|
TODO: to be completet

## installation with Intelij

1. get data with 
    ```
    git clone 
    ```
2. open Intelij
    * open File/New/Project from existing Sources
    * select Folder of cloned repo
    * use gradle as build tool
    * ![intelij_gradle_config](sonstiges/intelij_gradle_config.JPG)
    * ignore maven popups


3. after gradle build everything
    * open Run/Edit_Configurations
        * add a new Application with the green plus
        * ![intelij_run_configuration](sonstiges/intelij_run_configuration.JPG) 

4. click Run/Run'StrategoApp'