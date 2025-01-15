# IF ( IS INFECTED )
By Justine Capili and Malena del Rosario

## Game Overview
The game is a multiplayer game where players control characters on a
game canvas. The objective of the game is to avoid getting infected while
trying to infect other players. The game ends when all players except one
are infected or when the time runs out.
## Player Characters
There are three player characters in the game:

**P1**: Controlled by Player 1

**P2**: Controlled by Player 2

**P3**: Initially infected player

## Instructions:
The client will connect to the game by entering the IP address and port
number. The game will only start once three people have connected. The
KEYS W, S, A, and D will be used to move the character UP, DOWN, LEFT,
and RIGHT respectively.

## Game Controls
**Movement**: Use the arrow keys (up, down, left, right) to move your
character
### Game Mechanics
**Infection**: The game starts with one player infected (P3). Infected players
can infect other players by coming into contact with them. This is done
through collision detection.

**Avoiding Infection**: Non-infected players (P1 and P2) must avoid contact
with infected players to prevent getting infected.

**Time Limit**: The game has a time limit of 30 seconds. If zombies fail to
infect all human players, humans win and zombies lose. If zombies
successfully infect all humans, they win.

**Winner Determination**: The game ends when either all players are
infected or when the time runs out.

## Screenshots
<img width="599" alt="game" src="https://github.com/user-attachments/assets/07409eeb-fbe8-4fed-b83a-03e92d3e021e" />
