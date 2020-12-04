# A Game of Whist

The entry point to the application is “./src/Whist.java”

This project aims to simulate a card game called Whist. A GUI based on the “JGameGrid.jar” library has been created. Software design patterns and principles have been applied to allow for different types of NPCs: random, legal, and smart. A random NPC plays random cards from their playing hand, and does not account for the rules of the game. A legal NPC accounts for the rules of the game when making their move. A smart NPC uses relevant information from the round to select a desirable legal card from its playing hand. Its performance is better than that of the random and legal NPC. The purpose of designing an extensible system is to allow for interchangeable game configurations and future developments on the types of NPCs. In order to plan for this, two models were created (a static domain class model and a static design class model).

## Table of Contents

1. [Game Instructions](#game-instructions)
2. [The Properties Files](#the-properties-files)
3. [Creating Models](#creating-models)
4. [Explanation of Design Patterns and Principles Used](#explanation-of-design-patterns-and-principles-used)
5. [Application Demo](#application-demo)

## Game Instructions

* A random player along with a trump suit is selected to begin the game.
* A round consists of every player putting a card from their playing hand down onto the table.
* The objective of each round is to play the highest card of the starting card’s suit. 
* A player must play a card with the same suit as the starting card’s suit if they have one. Otherwise, they are free to play a card of any other suit (including the trump suit). 
* Once all players have completed their turn, the player that put the highest card of the starting card’s suit wins the round and receives a point. This player then starts the next round.
  * Note: If a player puts down a card which is a trump suit and all other players put a card of the starting card’s suit/any other suit, then the player that put the trump suit card wins the round.
  * If multiple players play a card of the trump suit, then the player that put the highest card of the trump suit wins the round. 
* Once a player obtains the winning score (specified in the properties file), then the game ends.
* If the players no longer have any cards left in hand at the end of a round and no player has won the game, then the cards are re-dealt and the trump suit is randomly selected again.

## The Properties Files

In order to simulate different game configurations with a range of NPC types, several properties files were created. Each has parameters indicating the number of players, the number of starting cards per player, the winning score, whether or not the rules are enforced, if NPC cards are face up or face down, and the types of players. Since the random NPC does not take into consideration the rules of the game, we cannot enforce the rules otherwise an exception will be thrown and the game will force quit. There are three game configurations provided: “original.properties”, “legal.properties”, and “smart.properties”. These can be changed on line 78 of “./src/Whist.java”. The default game configuration run is “smart.properties”.

## Creating Models

To ensure that the implementation of the game elements are extensible in terms of creating new NPCs and executing different game configurations, a static domain model and a static design class model was constructed:

Click [here](https://github.com/NiphanSethi/A_Game_of_Whist/blob/master/Documentation/Static_Domain_Diagram.jpg) to view the static domain class model

Click [here](https://github.com/NiphanSethi/A_Game_of_Whist/blob/master/Documentation/Static_Design_Diagram.jpg) to view the static design class model 

<em>Note: All models are constructed using UML notation</em>

Several software design principles and patterns were considered when creating these models (this is described below)

## Explanation of Design Patterns and Principles Used

## Application Demo
