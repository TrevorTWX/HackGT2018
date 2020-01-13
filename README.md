# OrcaMap
## Inspiration
- We may experience difficulties looking for our cars or specific brands/things in a huge parking lot or a mall, but we are often disappointed by the fact that Google Maps doesn’t work because of weak signals. We realize the need for indoor navigation apps to fill the market vacancies, and we won’t be lost again in the next grocery shopping!

## What it does
- Our Android app provides a convenient way for static indoor navigation using low-cost QR Code solution for saving lots of money for businesses. Users can scan the nearest QR Code in the shopping mall or grocery and search for the place they want to go and the app will provide the shortest route from their current location to the destinations. Also, after parking cars in a huge parking lot, users can scan the QR Code at their parking space and press “save”. When they are leaving, they can scan any QR Code in the parking lot and press “to saved place”, and then the app can help them find their cars in the huge parking lot with no struggle. 

## How we built it
- We build our backend using Python and choose Android for frontend. We use flask for building the http server and it is hold safely and reliably on Google Cloud VM instance. We build almost every function from scratch by ourselves and design the user interface for easy understanding and better look. 

## Challenges we ran into
- Unlike most navigation app, we aim on indoor navigation which prevents us from using any existing routing API. So we have to manage, design and store graph information in our way, and we need to build our own path finding algorithm as well. Also, storing floor maps and displaying routes also require lots of effort without existing program or platform’s help. 

## What’s next
- We have built our user-end implementation. However, the project might need a business-end solution as well. That’s say a easy way (as easy as how user can use it currently) for businesses to add their floor map and have a business-end interface helping them build map files and QR Codes automatically should be the next step for this project. 
