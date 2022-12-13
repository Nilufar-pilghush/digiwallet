# Digital Wallet

The service is designed to transfer money between wallets. Few lines for the service.
- Create wallet for a given user with unique email. If wallet already exist, it return Bad Request Exception.
- Get the balance of a wallet. If wallet doesn't exist, it return Not Found Exception.
- Transfer money between the two exist wallet. Sending wallet should have balance greater than or equal to the transfer amount. If sender doesn't have sufficient balance, it throws bad request exception.

# Get Started

##### Testing
- To run tests, run the following script: `./test.sh` or `sh test.sh`. In case of failure, please try running test again or reach out to me.
##### Local
###### Pre-requisite
- Java 11
###### Command
- To run the service locally, run `sh run.sh` or alternatively, run with the following command based on `nux` or `windows` based system.
-  `./mvnw clean package -U quarkus:dev`
   OR
- `mvnw.cmd clean package -U quarkus:dev`

##### Tech
- framework: `https://spring.io/`
- language: `java11`
- build tool: `maven`
- Datastore: `MySql`


#### Create Wallet
It dose not include JWT because of time restriction!

#### Transaction between Wallets
Transfer amount between two existing account, with the precondition, sufficient balance exist in the sending account. If sender doesn't have sufficient balance, it throws error.


* In case of more information/help reach out at n.pilghoosh@gmail.com
