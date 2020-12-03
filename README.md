# A Game of Whist

This project has been adapted from an assignment given at The University of Melbourne

The entry point to the application is “./src/Whist.java”

This project aims to simulate a card game called Whist. A GUI based on the “JGameGrid.jar” library has been created. Software design patterns and principles have been applied to allow for different types of NPCs: random, legal, and smart. A random NPC plays random cards from their playing hand, and does not account for the rules of the game. A legal NPC accounts for the rules of the game when making their move. A smart NPC uses relevant information from the round to select a desirable legal card from its playing hand. Its performance is better than that of the random and legal NPC. The purpose of designing an extensible system is to allow for interchangeable game configurations and future developments on the types of NPCs. In order to plan for this, two models were created (a static domain class model and a static design class model).

## Table of Contents

1. [Game Instructions](#game-instructions)
2. [The Properties Files](#the-properties-files)
3. [Creating Models](#creating-models)
4. [Explanation of Design Patterns and Principles Used](#explanation-of-design-patterns-and-principles-used)
5. [Application Demo](#application-demo)

## Game Instructions

## The Properties Files

## Creating Models

To ensure that the implementation of the game elements are extensible in terms of creating new NPCs and executing different game configurations, a static domain model and a static design class model was constructed:

Click [here](https://github.com/NiphanSethi/A_Game_of_Whist/blob/master/Documentation/Static_Domain_Diagram.jpg) to view the static domain class model

Click [here](https://github.com/NiphanSethi/A_Game_of_Whist/blob/master/Documentation/Static_Design_Diagram.jpg) to view the static design class model 

<em>Note: All models are constructed using UML notation</em>

Several software design principles and patterns were considered when creating these models (this is described below)

## Explanation of Design Patterns and Principles Used

## Application Demo
