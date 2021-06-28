THIRTY
-------------------------------------------------------------
Android dice game made in android studio using kotlin.

Thirty is a single player game and has similarities to yatzy. 
The game operates by throwing six dices in 3 turns. The
player can choose which dices they want to save and which
ones should be re-rolled. A game consists of 10 of these rounds 
meaning it can be up to a total of 30 throws. After the 3rd throw
the player gets to choose gamemode and what pairs should be submitted. 

GAMEMODES
---------------------------------------------------------------------------------------
- Low	Every dice with a value of 3 or less gives points equal to its value
- 4	Each combination of one or more dice with a sum of 4 gives points equal to its value
- 5	Each combination of one or more dice with a sum of 5 gives points equal to its value
- 6	Each combination of one or more dice with a sum of 6 gives points equal to its value
- 7	Each combination of one or more dice with a sum of 7 gives points equal to its value
- 8	Each combination of one or more dice with a sum of 8 gives points equal to its value
- 9	Each combination of one or more dice with a sum of 9 gives points equal to its value
- 10	Each combination of one or more dice with a sum of 10 gives points equal to its value
- 11	Each combination of one or more dice with a sum of 11 gives points equal to its value
- 12	Each combination of one or more dice with a sum of 12 gives points equal to its value

HOW TO PLAY
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 - Press "throw" and you should see some dice pop up
 - Click on the dice you want to save (remember, you select gamemode at the end of a round and you can save a dice and then unsave it)
 - Press "throw" until the last round
 - On the last round the submit pairs button pop up, click on the dice you want to pair, they will turn red (you can select one, two, three, four, five or six dice)
 - When you feel content with your red dices (or a "pair"), press the "Submit pairs" button which then calculates a score based on your gamemode and the sum of the red dices
 - Repeat this until all dices are red (when you have paired all dice)
 - When you have played a round the gamemode is no longer available
 
NOTE: Pairs do not matter when playing the low gamemode since it will score based on the individual dices and not pairs!

![start](https://user-images.githubusercontent.com/28981311/123262508-3d476880-d4f8-11eb-8adf-ffcb3a0320c3.png)
![paired](https://user-images.githubusercontent.com/28981311/123262519-3fa9c280-d4f8-11eb-8ce8-82ba1373a232.png)
![pairError](https://user-images.githubusercontent.com/28981311/123262527-41738600-d4f8-11eb-8cea-6c87e5b19a6a.png)
![image](https://user-images.githubusercontent.com/28981311/123618009-15157d80-d808-11eb-8633-40317154dd0a.png)
![image](https://user-images.githubusercontent.com/28981311/123617904-ffa05380-d807-11eb-92b6-2507086c39d1.png)
