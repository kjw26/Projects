import java.util.*;
public class p1_17s_kjw26 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Name:   Khadir Williams
		//UCID:   kjw26
		
		
		Scanner scan = new Scanner(System.in); 							// Initialize a scanner for user input
		int inputIndex = 0; 											//Integer variable to keep track of the index of the character being processed by the DFA
		
		int dfaState = 1; 												//Integer to keep track of the current state of the string in the DFA
		
		String answer;									 				//String to hold the yes or no answer to the "would you like to enter a string" question
		
		String submission; 												//String variable to hold the string to be submitted to the DFA
		
		System.out.println("Would you like to enter a string? Enter \"y\" for \"yes\" and \"n\" for \"no\"."); 
		
		answer = scan.next(); 											//scan for for input 
		
																		//check for either yes or no. If yes prompt for web address, if no quit, if any other response quit.
		
		if(answer.equals("n")){ 
			System.out.println("Program will terminate");
			System.exit(0);
		}
		
		else if(answer.equals("y")){
			System.out.println("Please enter a web address");
		}
		
		else{
			System.out.println("Invalid answer. Program will terminate.");
			System.exit(0);
			
		}
		
		submission = scan.next(); 										//scan for string input
		
		System.out.println(submission);									//print the input
		
		stageOne(submission, inputIndex, dfaState); 					//Our first function call to submit the string into the DFA. The Parameters were initialized above.

	}
	
	
	static void stageOne(String input, int index, int state){ 
		
		//stageOne handles states 2,4,5, and 6. 
		
		System.out.println("We begin with q" + state + ":"); 			//print the first state in the DFA
		char letter = input.charAt(index);								//Store the first character from the input string
		index++; 														// increase the index by one to prepare to read the next character
		
		
		/* The nested if statements below check the first four characters for the sequence "www.". 
		 * The characters are checked one by one and each character progresses the string further into the states of the DFA.
		 * If the first four characters do not match the sequence, the string is not of the form L1 and is passed into 
		 * to be tested for L2 stageTwo. 
		 */
		
		if(letter == 'w') //check for the first w
		{
			state = 2; 															//state q2 of the DFA
			System.out.println("Character: " + letter + " | State: q" + state); //Print the current character and the state that it is in
			
			if(index == input.length())
			{
				//String ends at this character, then the string is rejected by the DFA since this is not an accept state. The program then exits
				
				System.out.println("Ended in state q" + state+ " string is rejected");
				System.exit(0);
			}
			
			letter = input.charAt(index);
			index++;
			
			if(letter == 'w')
			{
				state = 4;
				System.out.println("Character: " + letter + " | State: q" + state);
				if(index == input.length())
				{
					System.out.println("Ended in state q" + state+ " string is rejected");
					System.exit(0);
				}
				letter = input.charAt(index);
				index++;
				
				if(letter == 'w')
				{

					state = 5;
					System.out.println("Character: " + letter + " | State: q" + state);
					if(index == input.length())
					{
						System.out.println("Ended in state q" + state+ " string is rejected");
						System.exit(0);
					}
					letter = input.charAt(index);
					index++;
				
					if(letter == '.')
					{
						state = 6;
						System.out.println("Character: " + letter + " | State: q" + state);
						if(index == input.length())
						{
							System.out.println("Ended in state q" + state+ " string is rejected");
							System.exit(0);
						}
						letter = input.charAt(index);
						index++;
						
						stageTwo(input, index, state, letter); 
						
						//The string is of form L1 and is passed to stageTwo
						
					}
					else
					{
						stageTwo(input, index, state, letter);
						
						//if the 4th character is not a '.' then pass the string, the index of the next character to be checked, the current state of the DFA, and the current character to stageTwo
					}
				}
				else
				{
					stageTwo(input, index, state, letter);
					
					//if the 3rd character is not a 'w' then pass the string, the index of the next character to be checked, the current state of the DFA, and the current character to stageTwo
				}
			}
			else
			{
				stageTwo(input, index, state, letter);
				
				//if the 2nd character is not a 'w' then pass the string, the index of the next character to be checked, the current state of the DFA, and the current character to stageTwo
			}
		}
		else
		{
			stageTwo(input, index, state, letter);
			
			//if the 1st character is not a 'w' then pass the string, the index of the next character to be checked, the current state of the DFA, and the current character to stageTwo
		}
	}

	
	static void stageTwo(String input, int index, int state, char letter){
		
		/* stageTwo checks for the existence of S2 in the string. 
		 * 
		 * 
		 */
		
		if(state <= 6 && !(Character.isLetter(letter))) 
		{
			/*If there if the string is in state 2,4,5, or 6 and the next character is not a 
			  letter then the DFA cannot continue and we move into our trap state*/
			
			trapState(input, index);
		}
		
		//Otherwise we continue 
		
		else
		{
			if(letter == 'c' && state == 6)
			{
				/* If the next character after the "." in "www." is a "c" then we move into stage three of the DFA because 
				 * the string may be in the form L2 where www.com is a valid string.
				 */
				stageThree(input, index, state, letter);
			}
			
			//Otherwise we move to state 3
			else
			{
				state = 3;
				
				/*State 3 allows for as many lower-case Roman letters as needed, so we continue printing characters in state 3 
				 * until the next character is not a letter
				 */
				
				while(Character.isLetter(letter))
				{
					System.out.println("Character: " + letter + " | State: q" + state);
					
					if(index == input.length()) 			//State 3 is not an accept state so reject if the string ends in this state
					{
						System.out.println("Ended in state q" + state+ " string is rejected");
						System.exit(0);
					}
					
					letter = input.charAt(index); //grab the next char
					index++;					  //prepare for the next char
				}
				
				//There are no more characters to be printed in state 3 so we move to stageThree to handle S3
				
				stageThree(input, index ,state, letter);
			}
		}
	}
	
	static void stageThree(String input, int index, int state, char letter){
		
		/*
		 * stageThree checks for S3 in the string. It also handles strings that at first appear to be processed up to set 3.
		 * In this case, if the string is appears to be in S3 but is still in S2, the stageThree will pass the string back to stageTwo.
		 * 
		 * Example: input = www.commercial.com 
		 * 
		 * www.com is valid and reaches an accept state; however the string has not been completely processed therefore it is resubmitted to 
		 * stageTwo since the next char after 'm' is 'm'
		 * 
		 * In the case of www.com.ca or www.com.co.ca, where the next character is '.', the string would be resubmitted to stageThree.		 
		 */
		
		if(letter == '.') 
		{
			/*
			 * If the next character is '.' and the string is in state 3, then we move to state 7 in the DFA
			 * Else the state will be equal to 10 in which case the next state to move to is 11 so we can simple increment
			 */
			if(state == 3)
			{
				state = 7;
			}
			else
			{
				state++;
			}
			
			System.out.println("Character: " + letter + " | State: q" + state);			//print the '.' and get the next char if it exists
			if(index == input.length())
			{
				System.out.println("Ended in state q" + state+ " string is rejected");
				System.exit(0);
			}
			letter = input.charAt(index);
			index++;
		}
		
		if(letter != 'c')
		{
			 /*
			  * The only way to get to stageThree is by '.' or by 'c' and in S3 'c' must come after '.' so either the 
			  * character sequence for the one to two latest chars is '.' then 'c' or just 'c' up to this point. 
			  * If not, we move to trap state. 
			  */
			
			trapState(input, index);	
		}
		else
		{
			if(state == 7 || state == 6)
			{
				//If the previous state was 6 or 7 where the previous char was '.' then move to state 8, the next state in the sequence
				state = 8;
			}
			else
			{
				//otherwise the previous state was 11 where the previous char was '.'. The next state in the sequence is 12
				state = 12;
			}
			
			System.out.println("Character: " + letter + " | State: q" + state);				//print the current char and state
			if(index == input.length())
			{
				System.out.println("Ended in state q" + state+ " string is rejected");		//quit if the the next char doesn't exist
				System.exit(0);
			}
			letter = input.charAt(index);													//grab next char
			index++;
			
			if(letter == 'a')
			{
				/*
				 * If the current char is 'a' then the string has reached state 9. State 9 is an accept state so if the string
				 * ends, then it is accepted. If not, then it is passed back to stageTwo and is of the form L1 where the first two 
				 * characters of S2 are 'c' and 'a'. If the next char is a '.', this is a trap state.
				 */
				state = 9;
				System.out.println("Character: " + letter + " | State: q" + state);
				
				if(index == input.length())
					System.out.println("Ended in state q" + state+ " string is accepted");
				else
				{
					letter = input.charAt(index);
					index++;
					stageTwo(input, index, state, letter);

				}
			}
			
			else if(letter == 'o')
			{
				
				/*
				 * If the current char is an 'o' then we move to stage 10. If the string ends here it is rejected.
				 * grab the next char
				 * 
				 */
				
				state = 10;
				System.out.println("Character: " + letter + " | State: q" + state);

				if(index == input.length())
				{
					System.out.println("Ended in state q" + state+ " string is rejected");
					System.exit(0);
				}
				
				letter = input.charAt(index);
				index++;
				
				if(letter == 'm')
				{
					/*
					 * If the current char is 'm' then this is an accept state and if the string ends then it is accepted,
					 * otherwise, if the char is equal to 'm' and the string does not end, then stageTwo is called because the 
					 * string is of form L1 where the first three chars are 'c', 'o', and 'm'.
					 */
					state = 13;
					System.out.println("Character: " + letter + " | State: q" + state);
					
					if(index == input.length())
						System.out.println("Ended in state q" + state+ " string is accepted");
					else
					{
						
						letter = input.charAt(index);
						index++;
						stageTwo(input, index, state, letter);

					}
				}
				else if(letter == '.')
				{
					/*
					 * If the current char is '.' then resubmit to stageThree because S3 may be of the form co.ca
					 */
					stageThree(input, index, state, letter);
				}
				else
				{
					/*
					 * Else, resubmit to stageTwo because the string is of form L1 where the first two chars are 'c' and 'o'
					 */
					stageTwo(input, index, state, letter);
				}
			}
			else
			{
				/*
				 * Else, resubmit to stageTwo because the string is of form L1 where the first char is 'c'
				 */
				stageTwo(input, index, state, letter);		
			}
		}
		
	}

	static void trapState(String input, int index){
		
		/*
		 * When the string has input that is not recognized by the DFA, the string enters the trap state where it stays until all characters
		 * are read
		 */
		
		for(int i = index-1; i < input.length(); i++)
		{
			//loop through the remaining characters and print them all out with the a state of "Trap State"
			
			System.out.println("Character: " + input.charAt(i) + " | State: Trap State");
		}
	}
}

