# FinanceApp

CSV Format for Testing:
Username, Password, AccountList Size, Account info (all account info comes after)

### Valid Accounts: 

User              Pass  
Alex              123  
wert              bert  
foo               boo

### Gradle Setup

gradle needs java 11 installed

There are seperate projects for server and client

to run server:

	gradle :server:run

to run client:

	gradle :client:run

to run both:

	gradle run --parallel

After exiting client, server will run for 30 seconds before stopping execution