# QUEUE MANAGEMENT APPLICATION
## DESCRIPTION
A **Java Desktop** queues management application which assigns _clients to queues_ such that the _waiting time_ is _minimized_: 
it simulates a series of _N_ clients arriving for service, entering _Q_ queues, waiting, being served and finally leaving the queues. 
&nbsp;

All **clients** are generated when the simulation is started, and are _characterized_ by three parameters: \
$~~~$-> **ID** (a _number_ between 1 and N) \
$~~~$-> **tarrival** (_simulation time_ when they are _ready to enter the queue_)  \
$~~~$-> **tservice** (time interval or _duration_ needed _to serve the client_).
&nbsp;

Each _client_ is _added to a queue_ when its *tarrival* time is **greater** than or equal to the _simulation current time_. 
&nbsp;

_Input data_ for the application that should be inserted _by the user_ in the application’s user interface: \
$~~~$-> **Number of clients** (_N_) \
$~~~$-> **Number of queues** (_Q_) \
$~~~$-> **Simulation interval** (𝑡𝑠𝑖𝑚𝑢𝑙𝑎𝑡𝑖𝑜𝑛 𝑀𝐴𝑋)  \
$~~~$-> **Minimum and maximum arrival time** (𝑡𝑎𝑟𝑟𝑖𝑣𝑎𝑙 𝑀𝐼𝑁 ≤ 𝑡𝑎𝑟𝑟𝑖𝑣𝑎𝑙 ≤ 𝑡𝑎𝑟𝑟𝑖𝑣𝑎𝑙 𝑀𝐴𝑋) \
$~~~$-> **Minimum and maximum service time** (𝑡𝑠𝑒𝑟𝑣𝑖𝑐𝑒 𝑀𝐼𝑁 ≤ 𝑡𝑠𝑒𝑟𝑣𝑖𝑐𝑒 ≤ 𝑡𝑠𝑒𝑟𝑣𝑖𝑐𝑒 𝑀𝐴𝑋)
&nbsp;

At the _end of the simulation_, the application displays the following _elements_: \
$~~~$- **average waiting time**: \
$~~~~~~$->at _each period_ (second), a **temporary average** = _average of the waiting periods of all queues at that moment_ is calculated; \
$~~~~~~$->at the _end of the simulation_, **average waiting time** = _average_ of those _temporary averages_ is computed \
$~~~$- **average service time**: the _average of the service times_ of the _clients_ which have _been processed_ until the end of the simulation \
$~~~$- **peakhour**: the _time_ at which there were the _most clients_ in the queues 

## STRUCTURE
$~~$  **OOP** design of the application - The project is based on a _layered architecture_, which has the following advantages:
  1. **Code Maintenance** is easy: we can easily determine any kind of change in the code
  2. **Security**: the data-providing package isn’t affected by the other packages
  3. **Ease of development**: building time taken by the application will be small as all the layers can work together at the same time     
&nbsp;

$~~$  The project has 3 packages + the _App class_ (its purpose is to run the application):
  1. **Data Models** – contains the classes _modeling the application data_ \
$~~~~~~~~~~~~~~~~~~~~~~~~$- in this app: the **Server** and **Task** classes
  2. **Graphical User Interface** – contains the classes implementing the _graphical user interface_
  3. **Business Logic** – contains: \
$~~~~~~$-> the _class_ and the _interface_ which determine _each queue each client goes to_ (**Scheduler**, **Strategy**) \
$~~~~~~$-> the _class getting the inputs_ of the graphical user interface, _generating the simulation_ based on them and, at a given period, _giving updates about the state of the simulation_ (**Simulation Manager**)

&nbsp;

**FULL DOCUMENTATION OF THE PROJECT** - _Documentation.pdf_
