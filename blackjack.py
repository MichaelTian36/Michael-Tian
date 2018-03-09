import random 
card = ["A", 2, 3, 4, 5, 6, 7, 8, 9, 10, "J", "Q", "K"]*4
dealer = []
player = []

def take_card(person):
	a = random.choice(card)
	for i in range(0,len(card)-1):
		if card[i] == a:
			card.pop(i)
			person += [a]
			return 
	if card[-1] == a:
		card.pop()
	person += [a]

take_card(dealer)
take_card(dealer)

take_card(player)
take_card(player)

import time

print ""
print "___.   .__                 __         __               __    "
print "\_ |__ |  | _____    ____ |  | __    |__|____    ____ |  | __"
print " | __ \|  | \__  \ _/ ___\|  |/ /    |  \__  \ _/ ___\|  |/ /"
print " | \_\ \  |__/ __ \ \ \___|    <     |  |/ __ \ \ \___|    < "
print " |___  /____(____  /\___  >__|_ \/\__|  (____  /\___  >__|_ \ "
print "     \/          \/     \/     \/\______|    \/     \/     \/ "
print " "
time.sleep(1)
print "                 -------------------------"
print "                 |        WELCOME        |"
print "                 |       BLACKJACK       |"
print "                 -------------------------"
time.sleep(1)
print ""
print "GAME START"
time.sleep(1)
print "Here is your card"
print player 
time.sleep(1)
print "If you want to get another card (hit), please type answer yes."

answer=raw_input()
print "user said", answer


def sum_card(person):
	total = 0
	for i in person:
		total += i
	return total 

def change_card(person):
 	copycard = person[:]
 	copycard = [1 if i == "A" else i for i in copycard]
 	copycard = [10 if type(i) != int else i for i in copycard]
 	return copycard

average = sum_card(change_card(card)) / len(change_card(card))

def player_round(response):
	if response == "yes":
		return True 
	else:
		return False 

def dealer_round():	
	if sum_card(change_card(dealer)) + average < 21:
		return True
	else:
		return False


while player_round(answer) == True or dealer_round() == True:
	if player_round(answer) == True:
		take_card(player)
		print player 
		print "If you want to get another card (hit), please type answer yes"
		answer=raw_input()
		print "user said", answer
	if dealer_round() == True:
		take_card(dealer)
	

print "	     ================================"
print "	     |           Result             |"
print "	     ================================"
print ""
time.sleep(1)
print "~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
print "@         DEALER           @" 
print "~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
print "The dealer's cards are"
print change_card(dealer)
print "Total points of dealer is" 
print sum_card(change_card(dealer))

time.sleep(2)
print "~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
print "@         PLAYER           @"
print "~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
print "The player's cards are"
print change_card(player)
print "Total points of player is"
print sum_card(change_card(player))
print "~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
time.sleep(2)


if sum_card(change_card(dealer)) > 21:
	if sum_card(change_card(player)) > 21:
		print "Tie"
	else:
		print "The player win!"
if sum_card(change_card(dealer)) == 21:
	if sum_card(change_card(player)) == 21:
		print "Tie"
	else:
		print "The player lose!"

if sum_card(change_card(dealer)) < 21:
	if sum_card(change_card(player)) > 21:
		print "The player lose!"
	elif sum_card(change_card(player)) == 21:
		print "The player win!"
	elif	sum_card(change_card(dealer)) > sum_card(change_card(player)):
		print "The player lose!"
	else:
		print "The player win!"

print "~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
time.sleep(2)

print "                  /88888888888888888888888888\ "
print "                  |88888888888888888888888888/ "
print "                   |~~____~~~~~~~~~""""""""" ""
print "                  / \_________/"""""""""""""
print "                 /  |              \         \ "
print "                /   |  88    88     \         \ "
print "               /    |  88    88      \         \ "
print "              /    /                  \        | "
print "             /     |   ________        \       |        Good job, player!"   
print "             \     |   \______/        /       |  Hope you like our game!"
print "  / \         \     \____________     /        | "
print "  | |__________\_        |  |        /        / "
print " /---\           \_------'  '-------/       -- "
print " \____/,___________\                 -------/ "
print " ------*           |                    \ "
print "  ||               |      CS10           \ "
print "  ||               |   ___BJC___     ^    \ "
print "  ||               |     Thanks     | \    \ "
print "  ||               |    Pro.Dans    |  \    \ "
print "  ||               |    All TA's    |   \    \ "
print "  \|              /                /-----\/   / "
print "     -------------                |    |    / "
print "     |\--_                        \____/___/ "
print "     |   |\-_                       | "
print "     |   |   \_                     | "
print "     |   |     \                    | "
print "     |   |      \_                  | " 
print "     |   |        ----___           | "
print "     |   |               \----------| "
print "     /   |                     |     ----------""\ "
print " /^\--^--|                     |               |  \ "
print "|_______/                      \______________/    )"
print "                                              \___/ "

		


# print "-----------------------------"
# print "checking list"
# print "dealer"
# print dealer
# print "player"
# print player 
# print card 
# print change_card(card)
# print "sum_card and average"
# print sum_card(change_card(card))
# print average
# print "dealer"
# print change_card(dealer)
# print sum_card(change_card(dealer))
# print "player"
# print change_card(player)
# print sum_card(change_card(player))


