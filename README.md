# Tool Rental Application


## Introduction

The code here implements a basic tool rental application in Java. The application consists of 6 basic components...

- Rental Application (RentalApplication)
- Rental Module (RentalModule)
- Tool Service (ToolService)
- Price Service (PriceService)
- Periodic Discount Service (PeriodicDiscountService)
- Agreement Service (AgreementService)


## Components

Orchestration and other interactions between the following components happens at the interface level. Non-primitive data types are also passed between the components as interfaces


### Rental Application (RentalApplication)

Basically performs all the object instantiations including service class implementations, as well as that of the RentalModule. All the data used within the application is hard coded and supplied to the various service implementations via their respective constructors. As per the specification, no persistent storage is used. This class also accepts command line arguments that describe the rental information sought.


### Rental Module (RentalModule)

Orchestrates interactions between the three main service components (interfaces)...

- ToolService
- PriceService
- AgreementService

The PeriodicDiscountService is not directly referenced by the RentalModule class (it's consumed by the PriceService implementation)


### Tool Service (ToolService)

This looks up a tool (Tool type) based on the tool code passed in. This is implemented by the InlineToolService class that looks up the Tool from a java.util.Map type (Map<String, Tool>) that was provided to its constructor by the application (RentalAppplication).


### Price Service (PriceService)

Given a tool (Tool type), this service looks up rental pricing data based on the tool and, number of rental days, and an arbitrary discount. This is implemented by the InlinePriceService that has daily rental rates for a given tool type (ToolType type) passed in as a Map type by the application (RentalApplication) upon its construction. It accounts for days for which no daily price is charged based on various periodic rules. These rules retrieved by calling the periodic discount service (PeriodicDiscountService).


### Periodic Discount Service (PeriodicDiscountService)

This is implemented by the InlinePeriodicDiscountService. For the given tool type (ToolType), the periodic discounts are looked up for that tool type and applied to be pricing by the Pricing Service. If more than one periodic discount applies to the tool type, these are sent back in an aggregate periodic discount (AgregatePeriodicDiscount) that contains the periodic discounts that apply. These periodic discount (PeriodicDiscount) instance determine, for the period, the dates for which no rental charge applies. The InlinePeriodicDiscountService is supplied its data via its constructor which is called by the application (RentalApplication).


### Agreement Service (AgreementService)

Renders the final rental agreement. This interface is implemented by the LineWriterAgreementService class writes each line of the rental contract to the LineWriter instance that was passed into its constructor. Its constructor, again, is called by the application (RentalApplication). The application (RentalApplication) itself implements the LineWriter interface, and prints input that's received to the console (STDOUT).


## Project Structure

This is a standard Maven multi-module / parent project. There is a parent POM file in the root directory and POM files within each of the modules. The dependency structure is as follows (the module name and its primary class are given)...

- application (RentalApplication) - classes and interfaces
  - module (RentalModule)
  - core (core classes / interfaces - eg. LineWriter)
  - tool (ToolService)
  - tool.inline (InlineToolService)
  - price (PriceService)
  - price.inline (InlinePriceService)
  - agreement (AgreementService)
  - agreement.inline (InlineAgreementService)
- module (RentalModule) - classes and interfaces
  - tool
  - price
  - agreement
- tool (ToolService) - interfaces only
  - core
- price (PriceService / PeriodicDiscountService) - interfaces only
  - core
  - tool
- agreement (AgreementService) - interfaces only
  - core
  - tool
  - price
- tool.inline (InlineToolService) - implementation
  - core
  - tool
- price.inline (InlinePriceService) - implementation
  - core
  - tool
  - price
- agreement.inline (LineWriterAgreementService) - implementation
  - core
  - tool
  - price
  - agreement

There are no third party dependencies except for JUnit. The important thing to note in this structure is that each 'implementation' modules, only depends on other 'interfaces only' module (no 'implementation' module depends on another 'implementation module). Likewise, 'interfaces only' modules only depend on other 'interfaces only' modules (no 'interfaces only' module depends on other 'implemetation' modules).

## Other Considerations

### Variable Naming Conventions

- lcl... local variables (local to scope of method)
- s_... static member variables
- m_... non-static member variables

### Currency Calculations

Currency calculations are all done in cents (i.e. integers) due to rounding errors that can very quickly creep in to decimal types (although not so much BigDecimal). For rounding requirements, these integer values are multiplied by 10, the required rounding calculation done, and the result divided by 10.

### Inversion of Control (IoC)

The rental application (RentalApplication) basically implements IoC by providing implementations to the interfaces the latter of which are used throughout the application flow. The application 'injects' those implementations into the whatever class needs them (those classes only referring to the corresponding interface and never the implementation itself)

### Testing

JUnit is employed for testing. Coverage is at 93%, reaching 100% is a case of diminishing returns.


### Logging

No logging is undertaken here. I also have a certain method of doing such which will be something I bring to the table if hired. Suffice to say, code that looks like this...
 
    int i = 0;

    while (true) {

      logger.debug("Calling performOperation " + i);

      if (m_SomeInstance.performOperation(i)) {
        break;
      }

      i++
    }

...represents OO denormalization as regards to the logging call (i.e. mixing business logic with logging logic that should otherwise be orthogonal - however, as in many cases, there can be exceptions and denormalization is warranted, eg. with database schema design for performance reasons, or code for similar reasons).

### 'User Friendly Message'

The specification says the following, 'Checkout should throw an exception with an instructive, user-friendly message...'. Since the word 'exception' is mentioned, it has been assumed that a stack trace print out (along with some user friendly descriptive text) is also considered a legitimate part of said message.

## System Requirements

This project was developed using IntelliJ / JDK 11 (specified in parent POM), and is built using Maven 3.8.6.

To build the application, having checked it out of this repository, navigate to the base directory and issue...

    mvn clean install

To run the application, from the base directory, navigate to application/target (or application\target if on Windows) directory and issue...

    java -cp .\application-0.0.1-SNAPSHOT.jar mp0224.rental.application.RentalApplication

This will print out instructions on how to actually call the program with useful command line arguments. Those arguments are as follows...

1. Tool code (case insensitive, valid values are CHNS, LADW, JAKD, JAKR)
2. Start date of rental (MM/DD/YYYY) format (can be in the  past, eg. for testing purposes)
3. Rental day count
4. Discount percentage to be applied to the final rental price

It should be noted that the JAR file above was built using the Maven Shader plug-in and therefore contains all the necessary classes.

An example of what constitutes useful command line arguments is as follows...

    java -cp .\application-0.0.1-SNAPSHOT.jar mp0224.rental.application.RentalApplication CHNS 1/1/2024 89 50

## Appendix

### Documentation

Design documentation is available in Confluence. Authorized parties will receive a link and / or other details when requested. For such details, please e-mail [Matthew Phillips (mp0224@webfuture.com)](mailto:mp0224@webfuture.com). 

### Copyright

The design document "Tool Rental Application Design", and all artifacts contained within the series of repositories under the following URL https://github.com/mp022401 are © 2024 Matthew Phillips. All rights reserved. 