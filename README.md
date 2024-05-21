# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

##Chess Design -- Phase 2
https://sequencediagram.org/index.html?presentationMode=readOnly&shrinkToFit=true#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdOUyABRAAyXLg9RgdOAoxgADNvMMhR1MIziSyTqDcSpymgfAgEDiRCo2XLmaSYCBIXIUNTKLSOndZi83hxZj9tgztPL1GzjOUAJIAOW54UFwtG1v0ryW9s22xg3vqNWltDBOtOepJqnKRpQJoUPjAqQtQxFKCdRP1rNO7sjPq5ftjt3zs2AWdS9QgAGt0OXozB69nZc7i4rCvGUOUu42W+gtVQ8blToCLmUIlCkVBIqp1VhZ8D+0VqJcYNdLfnJuVVk8R03W2gj1N9hPKFvsuZygAmJxObr7vOjK8nqZnseXmBjxvdAOFMLxfH8AJoHYckYB5CBoiSAI0gyLJkHMNkjl3ao6iaVoDHUBI0HfAMUCDBYlgOLCQUKDddw-Gsv0+J4bSWXY+gBc5NyVbUhxgBAEKQNBYXgxDUXRWJsUHXVe2TVNUhQEBmzNOFSMLJlkzdDlyF5fl-QPUUJQgKUbh7ItNOnAdlT4ix0QnKcCVkllU2NTJM2zXNGILMyNIVUttO9X19M-MjOwbc82yjGMRx8l0Sx4ycVTC7MIrQeyExnLjd1EoSVzXTA6JBBL6IGAyxkA68vj-C9vxvaj73QjBn1fd9SpC2qqvC-8OoOECwO8PxAi8FA21E3xmGQ9JMkwB88isnd5wqaRdPqLlmhaAjVCI7pqvHerMqBErdoA-4Cqymjt0SviBPscaRIQ8bxIxKTrJk8znKMFBuDchtYWO9S4q08plr5VbktHC9ZlI8VJXB2K+0sy7wRgWyxGkxHCvKG7HrUfLCq3airj6YZVHsb8yB8fUxXVBAwhu8kOBgcaONvIrClmsBmrfXpidJ8nKZJamNTphCGaZrMWb6zwBsgyFGZ5aEYAAcXzVlJtQmbGuYYrFqVrk8Jaex8x2rqL1ZtlMb3PpjqPVZOMOi7Byx6EVdGVRYWQWJXbUJ7JPSwxEycg0QAUpT3Jzf74Yswoy25PkBXB1KYeMuGkxZAonZRqA7PRxz3oNckwG992AYRmPtLjvSYAAKmTqUjbdqO-Pmq6yVVhQoCgYBLH9rdLc9ovVbyhB13OgninoqYG7UcZKn6aePWkWeAEYnwAZgAFieFDMhUu4Vi+HQEFAZSyoPp5p69fMJg4mBGioie2fgLWuffKfVdnip5-zReV-Xrepg71NGfG+Twj4nz3oeUB79RhX1GDfPYd9eqcH6hBQI2AfBQGwNweArlDDexSFNNCORtYt2wrUBo61p4mxSv+Vql8oF9BZtRAoltv6wMYSzQqCVkZphNN7WEcA8G+yxP7AoacC7oEoPwn+0hYSl2TP5T0FYqzT3bDGaeGgJGqB4Ulb2i8xH5EtsMSw3szoOwzo-K4MCUC-3KKvTeMAH4LQKBzV+vQbF2JgA4jeTizAoOlmggIlgvoCWSDAAAUhAISyt8yBHAUpTWJDMJWMqFUSkBtqHBFNugd82Du6UDgBAASUBZgL2kM4korDzpExthMEx4dUozGeMfEJUAiklJWAAIR5AoOAABpL45SVg+K4edXRfEABW0ThJRNyigNEz1DHaM5K9QO+cUyGlDs2cOf0cloAUc3dknJdIJ2OnXVOQcdH5EzqjZZVzSjAA4BwKorTzSkTKbI60bz2nFOgIc9QljeLlDuYOapDs26jEXt8gpvySnmLnECkoRNhkTFGSxH5HToDdN6QM5hj9XEvxfNzdhtil5ov-l8fJbSsVQBxX0wZyDQKBMGgELw3dn7plgMAbA2DCDxESIQjWHMUkLXKEtFaa1WjGHNgdOcqZuB4HkQi7iLdeGKrhC9Xib1fIGg4F9CkKAFAamVdooGMhJUXJHFDDoWirnmrICwGoAB1FGuhuB2o2aK1uWcc5rNojU31YhuFkPnP0EmZMJgUypjTMIIANUoGWJUygpw3EwGJa1CNKxo2C1jYaBNyx-GgSAA 
